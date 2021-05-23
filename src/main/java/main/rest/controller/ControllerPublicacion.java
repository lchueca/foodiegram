package main.rest.controller;


import main.application.service.PublicationService;
import main.domain.resource.ComentarioResource;
import main.domain.resource.PublicacionResource;
import main.domain.resource.ValoracionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/posts")
public class ControllerPublicacion {

    @Autowired
    private PublicationService service;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> upload(@RequestPart("text") String text, @RequestPart(value = "lat", required = false) String latitud, @RequestPart(value="lon", required = false) String longuitud, @RequestPart("image") MultipartFile image) {


        try {
            Double lat = latitud != null ? Double.parseDouble(latitud) : null;
            Double lon = longuitud != null ? Double.parseDouble(longuitud) : null;

            Integer userID = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            PublicacionResource publi = service.upload(userID, text, image, lat,  lon);
            return publi != null ? ResponseEntity.ok(publi) : ResponseEntity.notFound().build();
        }

        catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("F");
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(value="/{pubID}", method = RequestMethod.GET)
    public ResponseEntity<PublicacionResource> getPost(@PathVariable Integer pubID) {

        PublicacionResource publi =  service.getPost(pubID);
        return publi != null ? ResponseEntity.ok(publi) : ResponseEntity.notFound().build();

    }

    @RequestMapping(value="/{pubID}", method = RequestMethod.PUT)
    ResponseEntity<?> edit(@PathVariable Integer pubID, @RequestPart(value = "text", required = false) String text) {

        try {
            PublicacionResource publi = service.editPost(pubID, text);
            return publi != null ? ResponseEntity.ok(publi) : ResponseEntity.notFound().build();
        }

        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (NoPermissionException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }

    @RequestMapping(value="/{pubID}",method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Integer pubID) {
        try {
            PublicacionResource publi = service.deletePost(pubID);
            return publi != null ? ResponseEntity.ok(publi) : ResponseEntity.notFound().build();
        }

        catch (NoPermissionException e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }


    }

    //devuleve un JSON con todas la valoraciones de una publicacion
    @RequestMapping(value = "/{pubID}/ratings", method = RequestMethod.GET)
    public ResponseEntity<List<ValoracionResource>> getRatings(@PathVariable Integer pubID) {

        List<ValoracionResource> valoraciones = service.getRatings(pubID);
        return valoraciones != null ? ResponseEntity.ok(valoraciones) : ResponseEntity.notFound().build();

    }

    //setea o updatea la valoracion de un usuario en una publicacion
    @RequestMapping(value="/{pubID}/ratings", method = RequestMethod.POST)
    public ResponseEntity<?> setRating(@PathVariable Integer pubID, @RequestPart(value="score") String punt){

        try {
            Integer userID = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            ValoracionResource valoracion = service.setRating(pubID,  userID, Float.parseFloat(punt));
            return ResponseEntity.ok(valoracion);
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

   //devuele si un usuario a valorado una publicacion
   @RequestMapping(value="/{pubID}/ratings/{user}", method = RequestMethod.GET)
    public ResponseEntity<ValoracionResource> getRating(@PathVariable String user, @PathVariable Integer pubID){

        ValoracionResource val=  service.getRating(pubID, user);

       return val != null ? ResponseEntity.ok(val) : ResponseEntity.notFound().build();

    }

    //borra una valoracion dentro una publicacion de un usuario
    @RequestMapping(value = "/{pubID}/ratings", method=RequestMethod.DELETE)
    public ResponseEntity<ValoracionResource> deleteRating(@PathVariable Integer pubID){

        Integer userID = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        ValoracionResource val = service.deleteRating(pubID, userID);
        return val != null ? ResponseEntity.ok(val) : ResponseEntity.notFound().build();

    }


    @RequestMapping(value="/{pubID}/comments", method=RequestMethod.GET)
    public ResponseEntity<List<ComentarioResource>> getComments(@PathVariable Integer pubID) {

        List<ComentarioResource> comentarios = service.getComments(pubID);
        return comentarios != null ? ResponseEntity.ok(comentarios) : ResponseEntity.notFound().build();

    }


    @RequestMapping(value = "/{pubID}/comments",method = RequestMethod.POST)
    public ResponseEntity<?> setComment(@PathVariable Integer pubID, @RequestPart(value="text") String text){

        try{
            Integer userID = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            ComentarioResource comment = service.setComment(pubID, userID, text);
            return ResponseEntity.ok(comment);
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        
    }

}