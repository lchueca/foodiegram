package main.rest.controller;



import main.application.service.MensajeService;
import main.domain.resource.MensajeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/messages/")
public class ControllerMensajes {

    @Autowired
    MensajeService service;


    @RequestMapping(value="/{mensID}",method = RequestMethod.GET)
    public ResponseEntity<MensajeResource> getMensaje(@PathVariable Integer mensID) {

        MensajeResource mens =  service.getMensaje(mensID);
        return mens != null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();

    }

    @RequestMapping(value="/menssagesPost",method = RequestMethod.POST)
    public ResponseEntity<?> setMensaje(@RequestPart (value="user1") String userId1, @RequestPart (value="user2") String userId2,@RequestPart (value="mensaje") String mensaje){

        try {
            MensajeResource mens = service.setMensaje(Integer.parseInt(userId1), Integer.parseInt(userId2),mensaje);
            return ResponseEntity.ok(mens);
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }
    @RequestMapping(value="/{mensID}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMensaje(@PathVariable Integer mensID) {

        MensajeResource  mens= service.deleteMensaje(mensID);
        return mensID != null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();


    }
    @RequestMapping(value = "/messagesAll/{user}", method = RequestMethod.GET)
    public ResponseEntity<?> getMensajes(@PathVariable Integer user) {

        List<MensajeResource> mens = service.getMensajes(user);
        return mens !=  null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();

    }







}



