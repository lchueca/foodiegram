package main.rest.controller;

import main.application.service.PublicationService;
import main.application.service.UserService;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoUsuario;
import main.security.*;
import org.apache.catalina.Store;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value ="/pruebas")
public class ControllerPrueba {

    @Autowired
    private PublicationService postService;

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
    private int userId;

    //devuelve un id de usuario dado un nombre
    public int getUserByName(String userName) {

        UsuarioResource usuario = service.getUserByName(userName);
        return usuario.getId();
    }

    //Devuelve la lista de publicaciones de un usuario dado su nombre
    public List<String> getPosts(String user) {

        List<PreviewPublicacion> publicaciones = service.getPosts(user);
        List<String> listPosts = new ArrayList<>();
        for(PreviewPublicacion p : publicaciones){
            listPosts.add(p.getImage());
        }

        return listPosts;
    }

    //---------------------------------------LOG IN---------------------------------------//

    @GetMapping()
    public ModelAndView landingPage(Model model){

        ModelAndView modelAndView = new ModelAndView("landingPage");

        model.addAttribute("userLog", new UserForm());

        return modelAndView;
    }

    @PostMapping("/postLogin")
    public ModelAndView login(@Valid @ModelAttribute("userLog") UserForm user, HttpServletResponse response, Model model) {

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

           Usuario usuario = repoUsuario.findById(getUserByName(user.getUsername()));
           userId = repoUsuario.findByName(user.getUsername()).getId();
           model.addAttribute("postList", getPosts(user.getUsername()));
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
    public ModelAndView registerUser(@Valid @ModelAttribute("newUser") UserForm user, Model model) {

        try {
            UsuarioResource newUser = service.register(user.getUsername(), user.getPassword(), user.getEmail());
            model.addAttribute("userLog", new UserForm());
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
    ModelAndView personalPage(Model model){
       return new ModelAndView("userPage");
    }

    //---------------------------------------UPLOAD POST---------------------------------------//

    @GetMapping("/upload")
    ModelAndView uploadPost(Model model){
        model.addAttribute("newPost", new PostForm());
        return new ModelAndView("uploadPost");
    }

    @PostMapping("/postUpload")
    ModelAndView postUpload(@Valid @ModelAttribute("newPost") PostForm post,  Model model) {

        try {
            PublicacionResource publi = postService.upload(2, post.getText(), post.getImage(), Double.parseDouble(post.getLatitud()), Double.parseDouble(post.getLongitud()));
            return new ModelAndView("userPage");

        } catch (IOException e) {
            model.addAttribute("problem", e.getMessage());
            return new ModelAndView("problems");

        } catch (IllegalArgumentException e) {
            model.addAttribute("problem", e.getMessage());
            return new ModelAndView("problems");
        }

    }

    //---------------------------------------MANAGE ACCOUNT---------------------------------------//

    @GetMapping("/manageAccount")
    ModelAndView manageAccount(Model model){
        model.addAttribute("postSrc", new String());
        return new ModelAndView("manageAccount");
    }

    //---------------------------------------FRIENDS---------------------------------------//

    @GetMapping("/friends")
    ModelAndView friends(Model model){
        model.addAttribute("friends", "friends will be friends");
        return new ModelAndView("friends");
    }

    //---------------------------------------SEARCH---------------------------------------//

    @GetMapping("/search")
    ModelAndView search(Model model){
        model.addAttribute("search", "search");
        return new ModelAndView("search");
    }
}
