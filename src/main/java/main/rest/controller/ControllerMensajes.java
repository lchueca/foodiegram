package main.rest.controller;


import com.google.gson.Gson;
import main.persistence.entity.Mensaje;
import main.persistence.repository.RepoMensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/messages")
public class ControllerMensajes {

    @Autowired
    RepoMensaje repoMen;


    @RequestMapping(value = "/getMessage/{mensID}", method = RequestMethod.GET)
    public ResponseEntity<?> getMessage(@PathVariable String mensID) {

        try {
            Mensaje mensaje = repoMen.findOne(Integer.parseInt(mensID));

            if (mensaje == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A message with that id doesn't exist");
            else
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(mensaje));

        }

        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message id must be an integer.");
        }


    }
    // Devuelve una lista con todas los mensajes del usuario
    @RequestMapping(value = "/getAll/{user}", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@PathVariable String user) {

        try {

            List<Mensaje> mensa = repoMen.findByiduser1(Integer.parseInt(user));
            //cam isempty
            if (mensa.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user does not have messages.");
            else
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(mensa));

        }

        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user does not have messages.");
        }

    }
    //pinta feo
    @RequestMapping(value = "/post/{user}/{user2}", method = RequestMethod.POST)
    public ResponseEntity<?> getAll(@PathVariable String user, @PathVariable String user2, @RequestPart("mensaje") String mensaje)  {

        try  {
            Mensaje mens = new Mensaje(Integer.parseInt(user),Integer.parseInt(user2),mensaje);
            repoMen.save(mens);
            return ResponseEntity.status(HttpStatus.OK).body("Sent");
        }

        catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The receiver does not exist");
        }


    }
    @RequestMapping(value = "/delete/{mensId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMessage(@PathVariable String mensId)  {

        try  {

            repoMen.delete(Integer.parseInt(mensId));
            return ResponseEntity.status(HttpStatus.OK).body("Message deleted");
        }

        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message id must be an integer.");

        }
        catch(EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message id not found.");
        }

    }







}



