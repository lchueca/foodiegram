package main.rest.controller;

import main.application.service.UserService;
import main.domain.resource.UsuarioResource;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoUsuario;
import main.security.UserForm;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value ="/pruebas")
public class ControllerPrueba {

    @Autowired
    RepoUsuario repoUsuario;

    @Autowired
    private UserService service;

    private Model model;

    //pagina personal del usuario
    @GetMapping("/personalpage")
    ModelAndView personalPage(Model mode){
        ModelAndView modelAndView = new ModelAndView("userPage");
        return modelAndView;
    }

    //registro de usuario
    @GetMapping("/register")
    ModelAndView register(Model model){
        ModelAndView modelAndView = new ModelAndView("registerForm");

        model.addAttribute("user", new Usuario());
        return modelAndView;

    }

    @PostMapping("/process_register")
    public ModelAndView processRegister(UserForm user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        UsuarioResource newUser = service.register(user.getUsername(), user.getPassword(), user.getEmail());
        ResponseEntity.ok(newUser);
        //repoUsuario.save(newUser);

        return landingPage(model);
    }


    @GetMapping()
    public ModelAndView landingPage(Model model){

        ModelAndView modelAndView = new ModelAndView("landingPage");

        model.addAttribute("header", "Welcome to Foodiegram" );
        return modelAndView;
    }

}
