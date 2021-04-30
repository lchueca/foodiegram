package main.rest.controller;

import main.application.service.*;
import com.google.gson.Gson;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.entity.Valoracion;
import main.persistence.proyecciones.PreviewPublicacion;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import main.persistence.repository.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/users")
public class ControllerUsuario {

    @Autowired
    RepoUsuario repoUsuario;

    @Autowired
    RepoPublicacion repoPublicacion;

    @Autowired
    RepoValoracion repoVal;

    @Autowired
    private SendEmailService sendEmailService;

    @Value("${apache.rootFolder}")
    private String apacheRootFolder;

    @Value("${apache.address}")
    private String apacheAddress;

    // Devuelve una lista con todas las IDs de las publicaciones del usuario y las imagenes correspondientes.
    @RequestMapping(value = "/{user}/posts", method = RequestMethod.GET)
    public ResponseEntity<?> getPosts(@PathVariable String user) {

        try {

            List<PreviewPublicacion> resul = repoPublicacion.findByiduser(Integer.parseInt(user));

            if (resul == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A user with that id does not exist.");
            else
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(resul));

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User id must be an integer.");
        }

    }

    @RequestMapping(value = "/{user}/upload", method = RequestMethod.POST)
    public ResponseEntity<?> upload(@PathVariable String user, @RequestPart("text") String text, @RequestPart("loc") String loc, @RequestPart("image") MultipartFile image) {

        try {

            File folder = new File(apacheRootFolder + "/" + user);
            folder.mkdirs();

            FileOutputStream stream = new FileOutputStream(folder.getAbsolutePath() + "/" + image.getOriginalFilename());
            stream.write(image.getBytes());

            String address = String.format("%s/%s/%s", apacheAddress, user, image.getOriginalFilename());
            Publicacion publi = new Publicacion(text, Integer.parseInt(user), address, loc);
            repoPublicacion.save(publi);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("F");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Done.");

    }

    //no se si lo llegaremos a usar
    @RequestMapping(value = "/{user}/ratings", method = RequestMethod.GET)
    public ResponseEntity<?> getRatingsUser(@PathVariable String user) {
        try {

            List<Valoracion> valoracionU = repoVal.findByiduser(Integer.parseInt(user));

            if (!valoracionU.isEmpty())
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(valoracionU));
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This User has not rated any publication");

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("iduser must be an integer");
        }

    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestPart("user") String user, @RequestPart("passwd") String passwd, @RequestPart("email") String email) {

        if (user.length() >= 20) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The name is to long," + "\n"
                    + "please insert a name BELOW 20 characters");

        } else if (passwd.length() >= 256) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The PASSWORD is to long," + "\n"
                    + "please insert a password BELOW 20 characters");

        } else if (!email.contains("@")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email introduces is NOT valid" + "\n" +
                    "please insert a valid e-mail");
        }

        //


        else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            Usuario newuser = new Usuario(user, encoder.encode(passwd), null, email);
          /*  String token = UUID.randomUUID().toString();

            SecurityToken secToken = new SecurityToken(newuser, token);

            String topic = "Foodiegram:Confirm your registration";
            String body = "Este es tu codigo de activacion: " + secToken.getToken();*/

            //if's
            Random random=new Random(1);
            int a=random.nextInt(100000);
            String mensaje="Su codigo de verificacion es : "+a;
            sendEmailService.sendEmails("yefelipe78@gmail.com",mensaje, "topic");

            repoUsuario.save(newuser);


            return ResponseEntity.status(HttpStatus.OK).body("okay");
        }


    }
}

