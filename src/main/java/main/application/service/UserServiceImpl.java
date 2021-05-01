package main.application.service;

import main.domain.converter.PreviewPublicacionConverter;
import main.domain.converter.PublicacionConverter;
import main.domain.converter.UsuarioConverter;
import main.domain.converter.ValoracionConverter;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.domain.resource.ValoracionResource;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.entity.Valoracion;
import main.persistence.entity.Verifytoken;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import main.persistence.repository.RepoValoracion;
import main.persistence.repository.RepoVerifytoken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    PreviewPublicacionConverter converterPreview = new PreviewPublicacionConverter();
    PublicacionConverter converterPubli = new PublicacionConverter();
    ValoracionConverter converterVal = new ValoracionConverter();
    UsuarioConverter converterUser = new UsuarioConverter();

    @Autowired
    RepoUsuario repoUsuario;
    @Autowired
    RepoVerifytoken repoToken;
    @Autowired
    SendEmailService sendEmailService;

    @Autowired
    RepoPublicacion repoPubli;

    @Autowired
    RepoValoracion repoVal;

    @Value("${apache.rootFolder}")
    private String apacheRootFolder;

    @Value("${apache.address}")
    private String apacheAddress;


    @Override
    public UsuarioResource getUser(Integer user) {
        return converterUser.convert(repoUsuario.findOne(user));
    }

    @Override
    public List<PreviewPublicacion> getPosts(Integer user) {

        Usuario usuario = repoUsuario.findOne(user);

        if (usuario == null)
            return null;

        else {

            List<Publicacion> publicaciones = repoPubli.findByiduser(user);
            return publicaciones.stream().map(converterPreview::convert).collect(Collectors.toList());
        }
    }

    @Override
    public PublicacionResource upload(Integer user, String text, String loc, MultipartFile image) throws IOException {


        File folder = new File(apacheRootFolder + "/" + user);
        folder.mkdirs();

        FileOutputStream stream = new FileOutputStream(folder.getAbsolutePath() + "/"  + image.getOriginalFilename());
        stream.write(image.getBytes());

        String address = String.format("%s/%s/%s", apacheAddress, user, image.getOriginalFilename());
        Publicacion publi = new Publicacion(text, user, address, loc);
        repoPubli.save(publi);


        return converterPubli.convert(publi);
    }

    @Override
    public List<ValoracionResource> getRatings(Integer user) {

        Usuario usuario = repoUsuario.findOne(user);

        if (usuario == null)
            return null;

        else {

            List<Valoracion> valoracionU = repoVal.findByiduser(user);
            return valoracionU.stream().map(converterVal::convert).collect(Collectors.toList());
        }
    }

    @Override
    public UsuarioResource register(String user, String passwd, String email) throws IllegalArgumentException {

        if(user.length()>=20)
            throw new IllegalArgumentException("The name is to long, please insert a name BELOW 20 characters");

        else if(passwd.length()>=256)
            throw new IllegalArgumentException("The PASSWORD is to long, please insert a password BELOW 20 characters");


        else if(!email.contains("@"))
            throw new IllegalArgumentException("The email introduces is NOT valid, please insert a valid e-mail");

        else if (repoUsuario.findByemail(email) != null)
            throw new IllegalArgumentException("That e-mail is already registered");

        else {

            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            Random random=new Random(1);
            int codigo=random.nextInt(100000);
            String mensaje="Su codigo de verificacion es : "+codigo;

            sendEmailService.sendEmails("yefelipe78@gmail.com",mensaje, "topic");
            Usuario newUser = new Usuario(user, encoder.encode(passwd),null, email);
            repoUsuario.save(newUser);

            // Se hace otra consulta para ver que id le ha asignado la BD.
            newUser = repoUsuario.findByemail(email);

            Verifytoken verifyToken=new Verifytoken(newUser.getId(),codigo);

            repoToken.save(verifyToken);
            return converterUser.convert(newUser);
        }
    }
    public UsuarioResource verify(Integer token){//token  de entrada
       //token de entrada comparar token con la id del user
        //
            Verifytoken verToken =repoToken.findBytoken(token);
            if(verToken==null){
                return null;
            }

            else{

                Usuario user=repoUsuario.findOne(verToken.getIduser());
                user.setEnabled(true);
                repoUsuario.save(user);
                repoToken.delete(verToken);

            }


        return null;
    }
}
