package main.rest.controller;


import com.google.gson.Gson;
import main.persistence.IDs.IDMensajes;
import main.persistence.entity.Mensaje;
import main.persistence.repository.RepoMensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/messages")
public class ControllerMensajes {

    @Autowired
    RepoMensaje repoMen;


    @RequestMapping(value = "/getMessage/{mensID}/{User1Id}/{User2Id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMessage(@PathVariable String mensID,@PathVariable String User1Id,@PathVariable String User2Id) {

        try {
            Mensaje mensaje = repoMen.findOne(new IDMensajes(Integer.parseInt(mensID),Integer.parseInt(User1Id),Integer.parseInt(User2Id)));

            if (mensaje == null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A message with that id doesn't exist");
            else
                return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(mensaje));

        }

        catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message id must be an integer.");
        }


    }






}



