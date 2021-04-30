package main.rest.controller;


import main.application.service.UserService;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.domain.resource.ValoracionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/users")
public class ControllerUsuario {

    @Autowired
    private UserService service;


    // Devuelve una lista con todas las IDs de las publicaciones del usuario y las imagenes correspondientes.
    @RequestMapping(value = "/{user}", method = RequestMethod.GET)
    public ResponseEntity<UsuarioResource> getUser(@PathVariable Integer user) {

        UsuarioResource usuario = service.getUser(user);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    // Devuelve una lista con todas las IDs de las publicaciones del usuario y las imagenes correspondientes.
    @RequestMapping(value = "/{user}/posts", method = RequestMethod.GET)
    public ResponseEntity<List<PreviewPublicacion>> getPosts(@PathVariable Integer user) {

        List<PreviewPublicacion> publicaciones = service.getPosts(user);
        return publicaciones != null ? ResponseEntity.ok(publicaciones) : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{user}/upload", method = RequestMethod.POST)
    public ResponseEntity<?> upload(@PathVariable Integer user, @RequestPart("text") String text, @RequestPart("loc") String loc, @RequestPart("image") MultipartFile image) {

        try {
            PublicacionResource publi = service.upload(user, text, loc, image);
            return ResponseEntity.ok(publi);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("F");
        }

    }

    //no se si lo llegaremos a usar
    @RequestMapping(value = "/{user}/ratings", method = RequestMethod.GET)
    public ResponseEntity<List<ValoracionResource>> getRatingsUser(@PathVariable Integer user) {

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

    @RequestMapping(value = "verify", method = RequestMethod.POST)
    public ResponseEntity<?> verifyUser(@RequestPart("token") String token) {
        try {

            UsuarioResource newUser = service.verify(Integer.parseInt(token));
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }
}

