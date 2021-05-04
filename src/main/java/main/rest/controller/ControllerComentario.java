package main.rest.controller;

import main.application.service.ComentarioService;
import main.domain.resource.ComentarioResource;
import org.springframework.beans.factory.annotation.Autowired;
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
    ComentarioService service;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> editComment(@PathVariable Integer comID, @RequestPart(value="text") String text){

       try{
           ComentarioResource comment = service.editComentario(comID,text);
           return comment != null ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();
       }
       catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }

    }


    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable Integer comID) {

        ComentarioResource comment = service.deleteComentario(comID);
        return comment != null ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();


    }
}

