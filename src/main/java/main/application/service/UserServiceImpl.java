package main.application.service;

import main.domain.converter.*;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.UsuarioResource;
import main.domain.resource.Usuario_baneadoResource;
import main.domain.resource.ValoracionResource;
import main.persistence.entity.*;
import main.persistence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
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

    @Override
    public UsuarioResource verify(Integer token) {//token  de entrada
       //token de entrada comparar token con la id del user
        //
        Verifytoken verToken = repoToken.findByToken(token);

        if (verToken==null)
            return null;

        if(verToken.getExpiredate().before(new Date())) {
            repoToken.delete(verToken);
            return null;
        }
        Usuario newUser = repoUsuario.findByEmail(verToken.getEmail());
        newUser.setEnabled(true);
        newUser.setRole(RoleEnum.ROLE_USER);
        repoUsuario.save(newUser);
        repoToken.delete(verToken);


        return converterUser.convert(newUser);
    }

    @Override
    public Usuario_baneadoResource banUser(String user,String severity) throws IllegalArgumentException{
        //nombre es unico
        Usuario newUser=repoUsuario.findByName(user);
        newUser.setEnabled(false);
        int maxPenalty=5; //maximo
        int Severity; //local

        try {
             Severity = Integer.parseInt(severity);
        }
        catch(Exception e){
            throw new IllegalArgumentException("the type introduced in severity must be a number between 1-5");
        }

        Date date;
        if(Severity>maxPenalty||Severity<=0)
            return null;


        date=this.calculateDate(Severity);
        Usuario_baneado bannedUser=new Usuario_baneado(newUser.getId(),date);

        repoUsuario_baneado.save(bannedUser);
        repoUsuario.save(newUser);


        return converterBannedUser.convert(bannedUser);
    }


    @Override
    public Usuario_baneadoResource unbanUser(String user) throws IllegalArgumentException{
        Usuario newUser;
        Integer id;

        try {
            newUser = repoUsuario.findByName(user);
            newUser.setEnabled(true);
            id = newUser.getId();
        }

        catch(Exception e){
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

    @Override
    public List<Usuario_baneadoResource> getBannedUserList(){
        List<Usuario_baneado> listabaneado = repoUsuario_baneado.findAll();

        return listabaneado.stream().map(converterBannedUser::convert).collect(Collectors.toList());
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




    private String getWarningMessage(Integer num) {
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

    private Date calculateDate(Integer severity) {

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

        return new Date(cal.getTime().getTime());
    }

}
