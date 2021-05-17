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

        model.addAttribute("newUser", new Usuario());
        return modelAndView;

    }


    @PostMapping("/postRegister")
    public void processRegister(@ModelAttribute Usuario user) {

        System.out.println(user.getName() + ": " + user.getEmail() + ": " + user.getPasswd());
        /*
        try {
            UsuarioResource newUser = service.register(user.getUsername(), user.getPassword(), user.getEmail());
            return new ModelAndView("landingPage");
        }

        catch (NullPointerException e) {
            model.addAttribute("problem", "Invalid form.");
            return new ModelAndView("problems");
        }

        catch (IllegalArgumentException e) {
            model.addAttribute("problem",  e.getMessage());
            return new ModelAndView("problems");
        }
        */
       // return new ModelAndView("landingPage");
    }


    /*
    @PostMapping("/postRegister")
    public ModelAndView processRegister(@ModelAttribute Usuario user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPasswd());
        user.setPasswd(encodedPassword);

        UsuarioResource newUser = service.register(user.getName(), user.getPasswd(), user.getEmail());
        ResponseEntity.ok(newUser);
        //repoUsuario.save(newUser);

        return landingPage(model);
    }
    */

    @GetMapping()
    public ModelAndView landingPage(Model model){

        ModelAndView modelAndView = new ModelAndView("landingPage");

        model.addAttribute("userLog", new Usuario());

        return modelAndView;
    }

    @PostMapping("/postLogin")
    public void userLogAtributes(@ModelAttribute Usuario user){
        System.out.println(user.getName() + ": " + user.getPasswd());
    }

}
