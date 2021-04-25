package main.rest.controller;


import com.google.gson.Gson;
import main.persistence.IDs.IDvaloracion;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.entity.Valoracion;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import main.persistence.repository.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/publications")
public class ControllerPublicacion {

    @Autowired
    RepoPublicacion repoPubli;

    @Autowired
    RepoValoracion repoVal;

    @Value("${apache.address}")
    private String apacheAddress;


    @RequestMapping(value = "/post/{user}", method = RequestMethod.POST)
    public ResponseEntity<?> getAll(@PathVariable String user, @RequestPart("text") String text, @RequestPart("loc") String loc, @RequestPart("image") MultipartFile image)  {

        try  {

            File folder = new File("images/" + user);
            folder.mkdirs();

            FileOutputStream stream = new FileOutputStream("images/" + user + "/"  + image.getOriginalFilename());
            stream.write(image.getBytes());

            String address = String.format("%s/%s/%s", apacheAddress, user, image.getOriginalFilename());
            Publicacion publi = new Publicacion(text, Integer.parseInt(user), address, loc);
            repoPubli.save(publi);

        }

        catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("F");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Finish");

    }

    // Devuelve una lista con todas las IDs de las publicaciones del usuario y las imagenes correspondientes.
    @RequestMapping(value = "/getAll/{user}", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@PathVariable String user) {

        try {

            List<Publicacion> resul = repoPubli.findByiduser(Integer.parseInt(user));

            if (resul == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A user with that id does not exist.");
            else
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(resul));

        }

        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publication id must be an integer.");
        }

    }

    @RequestMapping(value = "/getPubli/{pubID}", method = RequestMethod.GET)
    public ResponseEntity<?> getPublicationById(@PathVariable String pubID) {

        try {
            Publicacion publi = repoPubli.findOne(Integer.parseInt(pubID));

            if (publi == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A publication with that id doesn't exist");
            else
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(publi));

        }

        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publication id must be an integer.");
        }


    }


    // Falta probar esto.
    @RequestMapping(value = "/edit/{pubID}", method = RequestMethod.POST)
    ResponseEntity<?> editPublication(@PathVariable String pubID, @RequestPart(value = "text", required = false) String text, @RequestPart(value = "loc", required = false) String loc) {

        if (text == null && loc == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A new text or location is required");

        else try {
            Publicacion publi = repoPubli.findOne(Integer.parseInt(pubID));

            if (publi == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A publication with that id doesn't exist");

            else {

                if (text != null)
                    publi.setText(text);

                if (loc != null)
                    publi.setLocalization(loc);

                repoPubli.save(publi);

            }
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(publi));

        }

        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publication id must be an integer.");
        }






    }


    @RequestMapping(value="/setValoracion/{user}/{publicacion}/{punt}",method = RequestMethod.GET)
    public ResponseEntity<?> setValoracion(@PathVariable String user, @PathVariable String publicacion, @PathVariable String punt){

        try{
            int score = Integer.parseInt(punt);
            if(score>0 &&score<5) {
                Valoracion valora = new Valoracion(Integer.parseInt(publicacion), Integer.parseInt(user),score);
                repoVal.save(valora);
            }
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Punt must be a integer between 0 and 5");
        }

        catch (NumberFormatException e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All the values must be an integers.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("OKAY");
    }

    @RequestMapping(value="/getValoracion/{user}/{publicacion}",method = RequestMethod.GET)
    public ResponseEntity<?> getValoracion(@PathVariable String user,@PathVariable String publicacion){

        try{
            Valoracion valor= repoVal.findOne(new IDvaloracion(Integer.parseInt(user),Integer.parseInt(publicacion)));
            if(valor==null)
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("this Valoration doesn't exist");
            else
                return  ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(valor));


        }

        catch (NumberFormatException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All the values must be an integers.");
        }

    }

    @RequestMapping(value = "/getValoracionAll/{publicacion}", method = RequestMethod.GET)
    public String getAllValoracion(@PathVariable String publicacion) {

        List<Valoracion> valoracionM = repoVal.findByidpubli(Integer.parseInt(publicacion));


        return new Gson().toJson(valoracionM);

    }





}