package main.application.service;

import main.domain.converter.*;
import main.domain.resource.*;
import main.persistence.entity.*;
import main.persistence.repository.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private PreviewPublicacionConverter converterPreview = new PreviewPublicacionConverter();
    private ValoracionConverter converterVal = new ValoracionConverter();
    private UsuarioConverter converterUser = new UsuarioConverter();
    private final MensajeConverter converterMens = new MensajeConverter();


    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoVerifytoken repoToken;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private RepoPublicacion repoPubli;

    @Autowired
    private RepoValoracion repoVal;

    @Autowired
    private RepoRole repoRole;

    @Value("${apache.rootFolder}")
    private String apacheRootFolder;

    @Value("${apache.address}")
    private String apacheAddress;

    @Value("${domain}")
    private String domain;

    private final Random random = new Random();


    @Override
    public UsuarioResource getUserByName(String user) {
        return converterUser.convert(repoUsuario.findByName(user));
    }

    @Override
    public List<PreviewPublicacion> getPosts(String user) {

        Usuario usuario = repoUsuario.findByName(user);

        if (usuario == null)
            return null;

        else {

            List<Publicacion> publicaciones = repoPubli.findByIduser(usuario.getId());
            return publicaciones.stream().map(converterPreview::convert).collect(Collectors.toList());
        }
    }



    @Override
    public List<ValoracionResource> getRatings(String user) {

        Usuario usuario = repoUsuario.findByName(user);

        if (usuario == null)
            return null;

        else {

            List<Valoracion> valoracionU = repoVal.findByIduser(usuario.getId());
            return valoracionU.stream().map(converterVal::convert).collect(Collectors.toList());
        }
    }

    @Override
    public UsuarioResource register(String user, String passwd, String email) throws IllegalArgumentException {

        if(user.length()>=20)
            throw new IllegalArgumentException("The name is to long, please insert a name BELOW 20 characters.");

        else if(passwd.length()>=256)
            throw new IllegalArgumentException("The PASSWORD is to long, please insert a password BELOW 20 characters.");


        else if(!email.contains("@"))
            throw new IllegalArgumentException("The email introduces is NOT valid, please insert a valid e-mail.");

        else if (repoUsuario.findByEmail(email) != null)
            throw new IllegalArgumentException("That e-mail is already registered.");

        else if (repoUsuario.findByName(user) != null)
            throw new IllegalArgumentException("That username is already taken.");


        else {

            int token;

            // Por si coincide que ese token ya exista (Dificil, pero bueno...)
            do {
                token = random.nextInt(100000000);
            }while(repoToken.findByToken(token) != null);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            Usuario newUser = new Usuario(user, encoder.encode(passwd),null, email);
            repoUsuario.save(newUser);

            Verifytoken verifyToken=new Verifytoken(email, token);
            repoToken.save(verifyToken);


            // Se envia el email de confirmacion
            String mensaje="Enlace de verificación: " + "http://" + domain + ":8080/users/verify/" + token;
            String topic="Confirmación de correo electrónico en foodiegram.";
            sendEmailService.sendEmails(email, mensaje, topic);




            return converterUser.convert(newUser);

        }
    }
    public UsuarioResource verify(Integer token) {//token  de entrada
       //token de entrada comparar token con la id del user
        //
        Verifytoken verToken = repoToken.findByToken(token);

        if (verToken==null)
            return null;

        Usuario newUser = repoUsuario.findByEmail(verToken.getEmail());
        newUser.setEnabled(true);
        repoUsuario.save(newUser);
        repoToken.delete(verToken);

        repoRole.save(new Role(newUser.getId(),RoleEnum.ROLE_USER));
        return converterUser.convert(newUser);
    }


}
