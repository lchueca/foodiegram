package main.rest.controller;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/admin")
public class ControllerAdmin {

    @Autowired
    private UserService service;

    @Value("${direccion}")
    private String direccionWeb;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTokenGenerator jwtGenerator;


    @RequestMapping(value="ban",method=RequestMethod.POST)
    public ResponseEntity<?> banUser(@RequestPart("user") String user,@RequestPart("severity") String severe) {
        try{

            Usuario_baneadoResource bannedUser=service.banUser(user,severe);
            return ResponseEntity.ok(bannedUser);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @RequestMapping(value="unban",method=RequestMethod.POST)
    public ResponseEntity<?> unbanUser(@RequestPart("user") String user) {
        try{
            Usuario_baneadoResource bannedUser=service.unbanUser(user);
            return ResponseEntity.ok(bannedUser);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @RequestMapping(value="/{user}",method=RequestMethod.DELETE)
    public ResponseEntity<?> delete(HttpServletRequest req, @PathVariable String user) {
        try{
            UsuarioResource bannedUser=service.deleteUser(user);
            return ResponseEntity.ok(bannedUser);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getBannedUser(HttpServletRequest request) {

        List<UsuarioResource> bulist = service.getBannedUserList();
        return bulist !=  null ? ResponseEntity.ok(bulist) : ResponseEntity.notFound().build();

    }



}
