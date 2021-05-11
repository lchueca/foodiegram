package main.rest.controller;



import main.application.service.MensajeService;
import main.domain.resource.MensajeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/messages/")
public class ControllerMensajes {

    @Autowired
    MensajeService service;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getMensajes(HttpServletRequest request) {

        List<MensajeResource> mens = service.getMensajes((Integer) request.getAttribute("tokenId"));
        return mens !=  null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();

    }

    //hace un upload con el mensaje
    @RequestMapping(value="/{user2}",method = RequestMethod.POST)
    public ResponseEntity<?> setMensaje(HttpServletRequest request, @PathVariable String user2, @RequestPart (value="text") String mensaje){

        try {
            MensajeResource mens = service.setMensaje((Integer)request.getAttribute("tokenId"), user2,mensaje);
            return ResponseEntity.ok(mens);
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


    //devuelve un JSON con el mensaje (segun el mensID)
    @RequestMapping(value="/{mensID}",method = RequestMethod.GET)
    public ResponseEntity<?> getMensaje(HttpServletRequest req, @PathVariable Integer mensID) {

        try {
            MensajeResource mens =  service.getMensaje(mensID, (Integer) req.getAttribute("tokenId"));
            return mens != null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();
        }

        catch (NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @RequestMapping(value="/{mensID}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMensaje(HttpServletRequest req, @PathVariable Integer mensID) {

        try {
            MensajeResource  mens = service.deleteMensaje(mensID, (Integer) req.getAttribute("tokenId"));
            return mensID != null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();
        }

        catch (NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }



    }

}



