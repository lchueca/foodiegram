package main.rest.controller;

import main.application.service.UserService;
import main.domain.resource.UsuarioResource;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoUsuario;
import main.security.AuthTokenGenerator;
import main.security.RefreshTokenGenerator;
import main.security.TokenRefresher;
import main.security.UserForm;
import org.apache.catalina.Store;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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

    @Value("${domain}")
    private String domain;

    @Autowired
    private AuthTokenGenerator authTokenGenerator;

    @Autowired
    private RefreshTokenGenerator refreshTokenGenerator;

    @Autowired
    private TokenRefresher tokenRefresher;

    private Model model;


    //---------------------------------------LOG IN---------------------------------------//

    @GetMapping()
    public ModelAndView landingPage(Model model){

        ModelAndView modelAndView = new ModelAndView("landingPage");

        model.addAttribute("userLog", new UserForm());

        return modelAndView;
    }

    @PostMapping("/postLogin")
    public ModelAndView login(@Valid @ModelAttribute("userLog") UserForm user, HttpServletResponse response) {

        try {
            UsernamePasswordAuthenticationToken userData = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            authenticationManager.authenticate(userData);
            // Generamos el token de autentificacion
            String authToken = authTokenGenerator.buildToken(user.getUsername(), 15);

            // Generamos el refresh token
            String refreshToken = refreshTokenGenerator.buildToken(user.getUsername(), 300);
            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setDomain(domain);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(18000);
            cookie.setPath("/users/refresh");


            response.addCookie(cookie);
            //añadir al model, id del usuario, lista de publicaciones....
            return new ModelAndView("userPage");
        }

        catch (NullPointerException e) {
            model.addAttribute("problem", "Invalid form.");
            return new ModelAndView("problems");
        }

        catch (BadCredentialsException ex) {
            model.addAttribute("problem", "Wrong credentials.");
            return new ModelAndView("problems");
        }

        catch (DisabledException e) {
            model.addAttribute("problem", "User is disabled. Confirm your account firs");
            return new ModelAndView("problems");
        }
    }

   //---------------------------------------REGISTER---------------------------------------//

    @GetMapping("/register")
    ModelAndView register(Model model){
        ModelAndView modelAndView = new ModelAndView("registerForm");

        model.addAttribute("newUser", new UserForm());
        return modelAndView;

    }

    @PostMapping("/postRegister")
    public ModelAndView registerUser(@Valid @ModelAttribute("newUser") UserForm user) {

        try {
            UsuarioResource newUser = service.register(user.getUsername(), user.getPassword(), user.getEmail());
            return new ModelAndView("landingPage");
        }

        catch (NullPointerException e) {
            model.addAttribute("problem", "Invalid form.");
            return new ModelAndView("problems");
        }

        catch (IllegalArgumentException e) {
            model.addAttribute("problem", e.getMessage());
            return new ModelAndView("problems");
        }

    }

    //---------------------------------------PERSONAL PAGE---------------------------------------//

    @GetMapping("/personalpage")
    ModelAndView personalPage(Model mode){
       return new ModelAndView("userPage");
    }
}
