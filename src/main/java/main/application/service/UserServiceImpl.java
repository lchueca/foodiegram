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
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private PreviewPublicacionConverter converterPreview = new PreviewPublicacionConverter();
    private ValoracionConverter converterVal = new ValoracionConverter();
    private UsuarioConverter converterUser = new UsuarioConverter();
    private Usuario_baneadoConverter converterBannedUser= new Usuario_baneadoConverter();
    private final MensajeConverter converterMens = new MensajeConverter();


    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoVerifytoken repoToken;
    @Autowired
    private RepoUsuario_baneado repoUsuario_baneado;
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

    @Value("${direccion}")
    private String direccionWeb;

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
            String mensaje="Enlace de verificación: " + direccionWeb + "/users/verify/" + token;
            String topic="Confirmación de correo electrónico en foodiegram.";
            sendEmailService.sendEmails(email, mensaje, topic);




            return converterUser.convert(newUser);

        }
    }
    public UsuarioResource verify(Integer token) {//token  de entrada
       //token de entrada comparar token con la id del user
        //
        Verifytoken verToken = repoToken.findByToken(token);

        if (verToken==null) {
            return null;
        }           //11     antes   ahora 12:
        if(verToken.getExpiredate().before(new Date())) {
            repoToken.delete(verToken);
            return null;
        }
        Usuario newUser = repoUsuario.findByEmail(verToken.getEmail());
        newUser.setEnabled(true);
        repoUsuario.save(newUser);
        repoToken.delete(verToken);

        repoRole.save(new Role(newUser.getId(),RoleEnum.ROLE_USER));
        return converterUser.convert(newUser);
    }

    @Override
    public Usuario_baneadoResource banUser(String user,String severity) throws IllegalArgumentException{
        //nombre es unico
        Usuario newUser=repoUsuario.findByName(user);
        newUser.setEnabled(false);
        Integer maxPenalty=5; //maximo
        Integer Severity; //local

        try {
             Severity = Integer.parseInt(severity);
        }catch(Exception e){
            throw new IllegalArgumentException("the type introduced in severity must be a number between 1-5");
        }
        Date date;
        if(Severity>maxPenalty||Severity<=0) {
            return null;
        }

        date=this.calculateDate(Severity);
        Usuario_baneado bannedUser=new Usuario_baneado(newUser.getId(),date);

        repoUsuario_baneado.save(bannedUser);
        repoUsuario.save(newUser);


        return converterBannedUser.convert(bannedUser);
    }

    public Date calculateDate(Integer severity){

        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime().getTime());
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        switch(severity){
            case 1:
                cal.add(Calendar.HOUR,24); //1 day
                break;
            case 2:
                cal.add(Calendar.HOUR,168); //1 week
                break;
            case 3:
                cal.add(Calendar.MONTH,1); //1 month
                break;
            case 4:
                cal.add(Calendar.MONTH,6); //6 month
                break;
            case 5:
                cal.add(Calendar.YEAR,50); //permanent
                break;

        }
        Date banDate=new Date(cal.getTime().getTime());
        return banDate;
    }

    @Override
    public Usuario_baneadoResource unbanUser(String user) throws IllegalArgumentException{
        Usuario newUser;
        Integer id;
        try {
            newUser = repoUsuario.findByName(user);
            newUser.setEnabled(true);
            id = newUser.getId();
        }catch(Exception e){
            throw new IllegalArgumentException("Usuario no existe");

        }
        Usuario_baneado bannedUser=repoUsuario_baneado.findById(id);
        repoUsuario_baneado.delete(bannedUser);
        newUser.setEnabled(true);

        return null;


    }
    @Override
    public UsuarioResource deleteUser(String user){

        Usuario newUser=repoUsuario.findByName(user);
        repoUsuario.delete(newUser);
        return null;
    }
    public List<UsuarioResource> getBannedUserList(){
        List<Usuario_baneado> listabaneado=repoUsuario_baneado.findAll();
        List<Usuario>lista=new ArrayList<>();

        for(int i=0;i<listabaneado.size();i++){
            lista.add(repoUsuario.findById(listabaneado.get(i).getId()));
        }
        return lista.stream().map(converterUser::convert).collect(Collectors.toList());
    }
    @Override
    public UsuarioResource sendWarning(String user,Integer type){

        Usuario newUser=repoUsuario.findByName(user);
        String email= newUser.getEmail();
        Date actualDate=new Date();
        String mensaje=getWarningMessage(type)+", fecha de la operacion: "+actualDate;
        String topic="AVISO: COMETISTES UNA INFRACCION EN FOODIEGRAM";
        sendEmailService.sendEmails(email, mensaje, topic);
        return null;
    }
    public String getWarningMessage(Integer num){
        String message="";

        switch(num){
            case 1:  //imagenes no apropiados
                message="Has subido fotos que incumplen con la normativa de foodiegram";
                message=message+", Este es tu primer aviso,al siguiente baneo";
                break;
            case 2://lenguaje ofensivo en comentarios
                message="Has empleado lenguaje inapropiado a la hora de comentar en la plataforma";
                message=message+", Este es tu primer aviso,al siguiente baneo";
                break;
            case 3://baneo + aviso de infraccion
                message="Has sido baneado,habiendo incumplido con las normativas de la plataforma mas de una vez";

        }

        return message;
    }
//user/ban
}
