package main.rest.controller;



import main.application.service.MensajeService;
import main.domain.resource.MensajeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/messages")
public class ControllerMensajes {

    @Autowired
    MensajeService service;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getMensajes() {

        Integer userID = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        List<MensajeResource> mens = service.getMensajes(userID);
        return mens !=  null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();

    }

    //hace un upload con el mensaje
    @RequestMapping(value="/{user2}",method = RequestMethod.POST)
    public ResponseEntity<?> setMensaje(@PathVariable String user2, @RequestPart (value="text") String mensaje){

        try {
            Integer userID = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            MensajeResource mens = service.setMensaje(userID, user2, mensaje);
            return ResponseEntity.ok(mens);
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(value="/{mensID}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMensaje(@PathVariable Integer mensID) {

        try {
            MensajeResource  mens = service.deleteMensaje(mensID);
            return mensID != null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();
        }

        catch (NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }



    }

}



