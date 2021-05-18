package main.rest.controller;
import main.application.service.ComentarioService;
import main.application.service.PublicationService;
import main.application.service.UserService;
import main.domain.resource.*;
import main.security.JWTokenGenerator;
import main.security.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/mod")
public class ControllerModerador {

    @Autowired
    private UserService service;

    @Value("${direccion}")
    private String direccionWeb;

    @Autowired
    private PublicationService pubService;

    @Autowired
    private ComentarioService comService;


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
