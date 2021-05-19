package main.rest.controller;

import main.application.service.ComentarioService;
import main.application.service.PublicationService;
import main.application.service.UserService;
import main.domain.resource.ComentarioResource;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

import javax.naming.NoPermissionException;
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/mod")
public class ControllerModerador {

    @Autowired
    private UserService service;

    @Autowired
    private PublicationService pubService;

    @Autowired
    private ComentarioService comService;


    @GetMapping
    public String xD(){
        return  "hola";
    }

    @RequestMapping(value="/deletePub",method=RequestMethod.DELETE)
    public ResponseEntity<?> deletePub(@RequestPart ("pubID")String pubID) {
        try{

            PublicacionResource deletedPost=pubService.deletePost(Integer.parseInt(pubID));
            return ResponseEntity.ok(deletedPost);
        }catch (IllegalArgumentException | NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @RequestMapping(value="/deleteComment",method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@RequestPart ("comID")String comID) {
        try{

            ComentarioResource deletedCom=comService.deleteComentario(Integer.parseInt(comID));
            return ResponseEntity.ok(deletedCom);
        }catch (IllegalArgumentException | NoPermissionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value="/sendWarning",method=RequestMethod.POST)
    public ResponseEntity<?> sendWarning(@RequestPart ("user")String user,@RequestPart ("type")String type) {
    try{
        UsuarioResource warning=service.sendWarning(user,Integer.parseInt(type));
        return ResponseEntity.ok(warning);
    }catch (IllegalArgumentException  e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }
}
