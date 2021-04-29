package main.rest.controller;


import main.application.service.PublicationService;
import main.domain.resource.ComentarioResource;
import main.domain.resource.PublicacionResource;
import main.domain.resource.ValoracionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/posts/{pubID}")
public class ControllerPublicacion {

    @Autowired
    private PublicationService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PublicacionResource> getPost(@PathVariable Integer pubID) {

        PublicacionResource publi =  service.getPost(pubID);
        return publi != null ? ResponseEntity.ok(publi) : ResponseEntity.notFound().build();

    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> edit(@PathVariable Integer pubID, @RequestPart(value = "text", required = false) String text, @RequestPart(value = "loc", required = false) String loc) {

        try {
            PublicacionResource publi = service.editPost(pubID, text, loc);
            return publi != null ? ResponseEntity.ok(publi) : ResponseEntity.notFound().build();
        }

        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(method = RequestMethod.DELETE)
    ResponseEntity<PublicacionResource> delete(@PathVariable Integer pubID) {

        PublicacionResource publi = service.deletePost(pubID);
        return publi != null ? ResponseEntity.ok(publi) : ResponseEntity.notFound().build();

    }

    //devuleve un JSON con todas la valoraciones de una publicacion
    @RequestMapping(value = "/ratings", method = RequestMethod.GET)
    public ResponseEntity<List<ValoracionResource>> getRatings(@PathVariable Integer pubID) {

        List<ValoracionResource> valoraciones = service.getRatings(pubID);
        return valoraciones != null ? ResponseEntity.ok(valoraciones) : ResponseEntity.notFound().build();

    }

    //setea o updatea la valoracion de un usuario en una publicacion
    @RequestMapping(value="/ratings/{user}",method = RequestMethod.POST)
    public ResponseEntity<?> setRating(@PathVariable Integer user, @PathVariable Integer pubID, @RequestPart(value="score") String punt){

        try {
            ValoracionResource valoracion = service.setRating(pubID, user, Integer.parseInt(punt));
            return ResponseEntity.ok(valoracion);
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

   //devuele si un usuario a valorado una publicacion
   @RequestMapping(value="/ratings/{user}",method = RequestMethod.GET)
    public ResponseEntity<ValoracionResource> getRating(@PathVariable Integer user, @PathVariable Integer pubID){

        ValoracionResource val=  service.getRating(pubID,user);

       return val != null ? ResponseEntity.ok(val) : ResponseEntity.notFound().build();

    }

    //borra una valoracion dentro una publicacion de un usuario
    @RequestMapping(value = "/ratings/{user}",method=RequestMethod.DELETE)
    public ResponseEntity<ValoracionResource> deleteRating(@PathVariable Integer user,@PathVariable Integer pubID){

        ValoracionResource val = service.deleteRating(pubID, user);
        return val != null ? ResponseEntity.ok(val) : ResponseEntity.notFound().build();

    }


    @RequestMapping(value="/comments", method=RequestMethod.GET)
    public ResponseEntity<List<ComentarioResource>> getComments(@PathVariable Integer pubID) {

        List<ComentarioResource> comentarios = service.getComments(pubID);
        return comentarios != null ? ResponseEntity.ok(comentarios) : ResponseEntity.notFound().build();

    }


    @RequestMapping(value = "/comments/{userID}",method = RequestMethod.POST)
    public ResponseEntity<?> setComment(@PathVariable Integer userID, @PathVariable Integer pubID, @RequestPart(value="text") String text){

        try{
            ComentarioResource coment= service.setComment(pubID,userID,text);
            return ResponseEntity.ok(coment);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        
    }

}