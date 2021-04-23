package main.rest.controller;


import com.google.gson.Gson;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/users")
public class ControllerUsuario {

    @Autowired
    RepoUsuario repo;


    // Devuelve la lista de todos los usuarios (Prueba).
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getAll() {
        List<Usuario> xD =  repo.findAll();

        return new Gson().toJson(xD);
    }
}