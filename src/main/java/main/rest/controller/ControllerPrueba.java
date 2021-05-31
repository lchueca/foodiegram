package main.rest.controller;


import main.application.service.PublicationService;
import main.application.service.UserService;
import main.application.service.manageAccountService.ManageFriends;
import main.application.service.manageAccountService.ViewImages;
import main.domain.converter.PublicacionConverter;
import main.domain.resource.AmigoResource;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.persistence.repository.RepoPublicacion;
import main.rest.forms.FriendForm;
import main.rest.forms.PostForm;
import main.rest.forms.SearchForm;
import main.rest.forms.UserForm;
import main.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value ="/pruebas")
public class ControllerPrueba {

    @Autowired
    private PublicationService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserService service;

    @Autowired
    private ManageFriends friendsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthTokenGenerator authTokenGenerator;

    @Autowired
    private RefreshTokenGenerator refreshTokenGenerator;

    @Autowired
    private LogoutTokenGenerator logoutTokenGenerator;



    //devuelve un id de usuario dado un nombre
    public UsuarioResource getUserByName(String userName) {

        UsuarioResource usuario = service.getUserByName(userName);
        return usuario;
    }



    ////---------------------------------------PROBLEMS---------------------------------------//

    @GetMapping("/problems/{type}")
    ModelAndView problema(Model model, @PathVariable Integer type) {

        switch(type) {

            case 1: {
                model.addAttribute("problem", "Invalid form.");
                break;
            }

            case 2: {
                model.addAttribute("problem", "Wrong credentials.");
                break;
            }

            case 3: {
                model.addAttribute("problem", "User is disabled. Confirm your account first.");
                break;
            }

            case 4: {
                model.addAttribute("problem", "Invalid arguments. Try again");
                break;
            }

            default: {
                model.addAttribute("problem", "An ERROR took place.");
            }
        }


        return new ModelAndView("problems");
    }

    //---------------------------------------LOG IN---------------------------------------//

    @GetMapping()
    public ModelAndView landingPage(Model model){

        ModelAndView modelAndView = new ModelAndView("landingPage");

        model.addAttribute("userLog", new UserForm());

        return modelAndView;
    }

    @PostMapping("/postLogin")
    public void login(@Valid @ModelAttribute("userLog") UserForm user, HttpServletResponse response, Model model) throws IOException {

        try {
            UsernamePasswordAuthenticationToken userData = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            authenticationManager.authenticate(userData);
            // Generamos el token de autentificacion
            String authToken = authTokenGenerator.buildToken(user.getUsername(), 15);
            Cookie cookieA = new Cookie("authToken",authToken);
            cookieA.setHttpOnly(true);
            cookieA.setMaxAge(900);
            cookieA.setPath("/");
            response.addCookie(cookieA);

            // Generamos el refresh token
            String refreshToken = refreshTokenGenerator.buildToken(user.getUsername(), 300);
            Cookie cookieR = new Cookie("refreshToken", refreshToken);
            cookieR.setHttpOnly(true);
            cookieR.setMaxAge(18000);
            cookieR.setPath("/users/refresh");



            response.addCookie(cookieR);

            String loginToken = logoutTokenGenerator.getToken(user.getUsername());

            Cookie loggedInCookie = new Cookie("loggedIn", loginToken);
            loggedInCookie.setPath("/");

            response.addCookie(loggedInCookie);

            // Se redirige al usuario a su pagina personal
            response.sendRedirect("/pruebas/me");
        }

        catch (NullPointerException e) {
            response.sendRedirect("/pruebas/problems/1");
        }

        catch (BadCredentialsException ex) {
            response.sendRedirect("/pruebas/problems/2");
        }

        catch (DisabledException e) {
            response.sendRedirect("/pruebas/problems/3");
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
    public void registerUser(@Valid @ModelAttribute("newUser") UserForm user, HttpServletResponse response, Model model) throws IOException {

        try {
            UsuarioResource newUser = service.register(user);
            //model.addAttribute("userLog", new UserForm());
            response.sendRedirect("/pruebas");
            //return new ModelAndView("landingPage");
        }

        catch (NullPointerException e) {
            response.sendRedirect("/pruebas/problems/1");
            //model.addAttribute("problem", "Invalid form.");
            //return new ModelAndView("problems");
        }

        catch (IllegalArgumentException e) {
            //model.addAttribute("problem", e.getMessage());
            //return new ModelAndView("problems");
            response.sendRedirect("/pruebas/problems/4");
        }
    }

    //---------------------------------------PERSONAL PAGE---------------------------------------//

    @GetMapping("/me")
    ModelAndView personalPage(Model model){
        String user = SecurityContextHolder.getContext().getAuthentication().getDetails().toString();

        model.addAttribute("search" , new SearchForm());
        model.addAttribute("postList", userService.getPosts(user));

        return new ModelAndView("userPage");
    }

    //---------------------------------------UPLOAD POST---------------------------------------//

    @GetMapping("/upload")
    ModelAndView uploadPost(Model model){

        model.addAttribute("newPost", new PostForm());

        return new ModelAndView("uploadPost");
    }

    @PostMapping("/postUpload")
    void postUpload(@Valid @ModelAttribute("newPost") PostForm post, HttpServletResponse response,  Model model) throws IOException {

        try {
            Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            PublicacionResource publi = postService.upload(userId, post);
            //model.addAttribute("search" , new SearchForm());
            //model.addAttribute("postList", getPosts(userId));
            //return new ModelAndView("userPage");
            response.sendRedirect("/pruebas/me");
        }

        catch (IOException e) {
            //model.addAttribute("problem", e.getMessage());
            //return new ModelAndView("problems");
            response.sendRedirect("/pruebas/problems");
        }

        catch (IllegalArgumentException e) {
            //model.addAttribute("problem", e.getMessage());
            //return new ModelAndView("problems");
            response.sendRedirect("/pruebas/problems/4");
        }

    }

    //---------------------------------------MANAGE ACCOUNT---------------------------------------//

    @GetMapping("/manageAccount")
    ModelAndView manageAccount(Model model){
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("postSrc", "");
        return new ModelAndView("manageAccount");
    }

    //---------------------------------------FRIENDS---------------------------------------//

    @GetMapping("/friends")
    ModelAndView friends(Model model){
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        List<String> friends = friendsService.getFriends(userId);

        model.addAttribute("userName", "");
        model.addAttribute("friendManagement", new FriendForm());
        model.addAttribute("friends", friends);
        return new ModelAndView("friends");
    }

    @PostMapping("/postFriends")
    void postFriends(@Valid @ModelAttribute("friendManagement") FriendForm friend, HttpServletResponse response, Model model) throws IOException {
        Integer userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());

        try{
            if(friend.getType().equals("add")){
                AmigoResource amigoResource = friendsService.addFriend(userId, friend.getFriendName());
            }else{
                AmigoResource amigoResource = friendsService.removeFriend(userId, friend.getFriendName());
            }
            //List<String> friends = friendsService.getFriends(userId);
            //model.addAttribute("friends", friends);
            //return new ModelAndView("friends");
            response.sendRedirect("/pruebas/friends");
        }
        catch (IllegalArgumentException e){
            //model.addAttribute("problem", e.getMessage());
            //return new ModelAndView("problems");
            response.sendRedirect("/pruebas/problems/4");
        }

    }

    //---------------------------------------SEARCH---------------------------------------//

    @PostMapping("/search")
    ModelAndView search(@Valid @ModelAttribute("search") SearchForm search, HttpServletResponse response, Model model){

        model.addAttribute("search", "searching : " + search.getText());
        return new ModelAndView("search");
    }

    //---------------------------------------Friends Page---------------------------------------//
    @GetMapping("/friendsPage")
    ModelAndView friendsPage(@Valid @ModelAttribute("userName") String friend, HttpServletResponse response,Model model){

        model.addAttribute("userName", friend);
        model.addAttribute("profilePic", getUserByName(friend).getImage());
        model.addAttribute("postList", userService.getPosts(getUserByName(friend).getName()));

        return new ModelAndView("friendsPage");
    }
}
