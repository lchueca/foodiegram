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

    //devuelve un JSON con el mensaje (segun el mensID)
    @RequestMapping(value="/{mensID}",method = RequestMethod.GET)
    public ResponseEntity<MensajeResource> getMensaje(@PathVariable Integer mensID) {

        MensajeResource mens =  service.getMensaje(mensID);
        return mens != null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();

    }

    //hace un upload con el mensaje
    @RequestMapping(value="/{user1}/{user2}",method = RequestMethod.POST)
    public ResponseEntity<?> setMensaje(@PathVariable String user1, @PathVariable String user2, @RequestPart (value="text") String mensaje){

        try {
            MensajeResource mens = service.setMensaje(user1, user2,mensaje);
            return ResponseEntity.ok(mens);
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }
    @RequestMapping(value="/{mensID}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMensaje(@PathVariable Integer mensID) {

        MensajeResource  mens = service.deleteMensaje(mensID);
        return mensID != null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();


    }

}



