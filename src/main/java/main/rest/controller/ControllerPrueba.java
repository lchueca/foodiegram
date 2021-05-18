package main.rest.controller;

import main.application.service.UserService;
import main.domain.resource.UsuarioResource;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoUsuario;
import main.security.UserForm;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping(value ="/pruebas")
public class ControllerPrueba {

    @Autowired
    RepoUsuario repoUsuario;

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;


    private Model model;

    //pagina personal del usuario
    @GetMapping("/personalpage")
    ModelAndView personalPage(Model mode){
        ModelAndView modelAndView = new ModelAndView("userPage");
        return modelAndView;
    }
    /*
    //registro de usuario
    @GetMapping("/register")
    ModelAndView register(Model model){
        ModelAndView modelAndView = new ModelAndView("registerForm");

        model.addAttribute("newUser", new Usuario());
        return modelAndView;

    }


    @PostMapping("/postRegister")
    public void processRegister(@ModelAttribute Usuario user) {

        System.out.println(user.getName() + ": " + user.getEmail() + ": " + user.getPasswd());

    }

    @GetMapping()
    public ModelAndView landingPage(Model model){

        ModelAndView modelAndView = new ModelAndView("landingPage");

        model.addAttribute("userLog", new UserForm());

        return modelAndView;
    }


    @PostMapping("/postLogin")
    public void userLogAtributes(@Valid @ModelAttribute("userLog") UserForm user){
        System.out.println(user.getUsername() + ": " + user.getPassword());
    }
    */
}
