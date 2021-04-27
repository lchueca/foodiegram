package main.rest.controller;

import com.google.gson.Gson;
import main.persistence.entity.Comentario;
import main.persistence.repository.RepoComentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;




@org.springframework.web.bind.annotation.RestController
@RequestMapping("/comments/{comID}")
public class ControllerComentario {

    @Autowired
    RepoComentario repoComen;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> editComment(@PathVariable String comID, @RequestPart(value="text") String text){
        try {
            Comentario comment = repoComen.findOne(Integer.parseInt(comID));

            if (comment == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A Comment with that id doesn't exist");

            else {

                if (text != null)
                    comment.setText(text);


                repoComen.save(comment);

            }
            return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(comment));

        }

        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment id must be an integer.");
        }


    }




    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable String comID) {

        try {
            repoComen.delete(Integer.parseInt(comID));

            return ResponseEntity.status(HttpStatus.OK).body("Ok");

        }

        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment id must be an integer.");
        }

        catch(EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A comment with such id does not exist.");
        }


    }
}


