package main.rest.controller;


import com.google.gson.Gson;
import main.persistence.IDs.IDvaloracion;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.entity.Valoracion;
import main.persistence.proyecciones.PreviewPublicacion;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import main.persistence.repository.RepoValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/posts/{pubID}")
public class ControllerPublicacion {

    @Autowired
    RepoPublicacion repoPubli;

    @Autowired
    RepoValoracion repoVal;

    @Value("${apache.address}")
    private String apacheAddress;

    @Value("${apache.rootFolder}")
    private String apacheRootFolder;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getPost(@PathVariable String pubID) {

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
    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> edit(@PathVariable String pubID, @RequestPart(value = "text", required = false) String text, @RequestPart(value = "loc", required = false) String loc) {

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


    @RequestMapping(value = "/ratings", method = RequestMethod.GET)
    public ResponseEntity<?> getRatingsPubli(@PathVariable String pubID) {

        try {
            List<Valoracion> valoracionM = repoVal.findByidpubli(Integer.parseInt(pubID));

            if(!valoracionM.isEmpty())
                return  ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(valoracionM)) ;
            else
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("This Publication has no rating");

        }
        catch (NumberFormatException e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("idPubli must be an integer");
        }

    }

    @RequestMapping(value="/ratings/{user}/{punt}",method = RequestMethod.GET)
    public ResponseEntity<?> setRating(@PathVariable String user, @PathVariable String pubID, @PathVariable String punt){

        try{
            int score = Integer.parseInt(punt);
            if(score>=0 &&score<=5) {

                Valoracion valora = new Valoracion(Integer.parseInt(pubID), Integer.parseInt(user),score);
                repoVal.save(valora);
            }
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Punt must be a integer between 0 and 5");
        }

        catch (NumberFormatException e ){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All the values must be an integers.");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this user must exists.");
        }


        return ResponseEntity.status(HttpStatus.OK).body("OKAY");
    }

    @RequestMapping(value="/ratings/{user}",method = RequestMethod.GET)
    public ResponseEntity<?> getRating(@PathVariable String user,@PathVariable String pubID){

        try{
            Valoracion valor= repoVal.findOne(new IDvaloracion(Integer.parseInt(pubID),Integer.parseInt(user)));
            if(valor==null)
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("this Valoration doesn't exist");
            else
                return  ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(valor));


        }

        catch (NumberFormatException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All the values must be an integers.");
        }

    }


}