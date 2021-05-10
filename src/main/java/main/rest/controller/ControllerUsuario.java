package main.rest.controller;


import com.google.gson.Gson;
import main.application.service.UserService;
import main.domain.resource.*;
//import main.security.JWTokenGenerator;
//import main.security.UserDetailsImpl;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/users")
public class ControllerUsuario {

    @Autowired
    private UserService service;

    @Value("${direccion}")
    private String direccionWeb;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private JWTokenGenerator jwtGenerator;

    @RequestMapping(value = "/{user}", method = RequestMethod.GET)
    public ResponseEntity<UsuarioResource> getUserByName(@PathVariable String user) {

        UsuarioResource usuario = service.getUserByName(user);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    // Si buscas /users/user/postID se te redirige a /posts/postID
    @RequestMapping(value = "/{user}/{pubID}", method = RequestMethod.GET)
    public void redirectToPost(HttpServletResponse httpServletResponse, @PathVariable String user, @PathVariable Integer pubID) {

        httpServletResponse.setHeader("Location", direccionWeb + "/posts/" + pubID);
        httpServletResponse.setStatus(302);
    }

    // Devuelve una lista con todas las IDs de las publicaciones del usuario y las imagenes correspondientes.
    @RequestMapping(value = "/{user}/posts", method = RequestMethod.GET)
    public ResponseEntity<List<PreviewPublicacion>> getPosts(@PathVariable String user) {

        List<PreviewPublicacion> publicaciones = service.getPosts(user);
        return publicaciones != null ? ResponseEntity.ok(publicaciones) : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{user}/upload", method = RequestMethod.POST)
    public ResponseEntity<?> upload(@PathVariable String user, @RequestPart("text") String text, @RequestPart("loc") String loc, @RequestPart("image") MultipartFile image) {

        try {
            PublicacionResource publi = service.upload(user, text, loc, image);
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

    //no se si lo llegaremos a usar
    @RequestMapping(value = "/{user}/ratings", method = RequestMethod.GET)
    public ResponseEntity<List<ValoracionResource>> getRatingsUser(@PathVariable String user) {

        List<ValoracionResource> valoraciones = service.getRatings(user);
        return valoraciones != null ? ResponseEntity.ok(valoraciones) : ResponseEntity.notFound().build();

    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestPart("user") String user, @RequestPart("passwd") String passwd, @RequestPart("email") String email) {
        try {
            UsuarioResource newUser = service.register(user, passwd, email);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(value = "verify/{token}", method = RequestMethod.GET)
    public ResponseEntity<?> verifyUser(@PathVariable Integer token) {

        try {

            UsuarioResource newUser = service.verify(token);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

    @RequestMapping(value = "/{user}/messages", method = RequestMethod.GET)
    public ResponseEntity<?> getMensajes(@PathVariable String user) {

        List<MensajeResource> mens = service.getMensajes(user);
        return mens !=  null ? ResponseEntity.ok(mens) : ResponseEntity.notFound().build();

    }

//    @RequestMapping(value="/login", method=RequestMethod.POST)
//    public ResponseEntity<String> login(@RequestBody String body) {
//
//        Gson g = new Gson();
//        UserDetailsImpl cred = g.fromJson(body, UserDetailsImpl.class);
//
//        try {
//            Authentication authenticate = authenticationManager.authenticate(
//                            new UsernamePasswordAuthenticationToken(
//                                    cred.getUsername(), cred.getPassword()
//                            )
//                    );
//
//            return ResponseEntity.ok(jwtGenerator.buildToken(cred.getUsername(), cred.getPassword()));
//        }
//
//        catch (BadCredentialsException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials.");
//        }
//
//        catch (DisabledException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is disabled.");
//        }
//    }


}

