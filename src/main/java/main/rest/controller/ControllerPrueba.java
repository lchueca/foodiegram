package main.rest.controller;


import main.application.service.PublicationService;
import main.application.service.SearchService;
import main.application.service.UserService;
import main.application.service.manageAccountService.ManageFriends;
import main.domain.resource.AmigoResource;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.rest.forms.*;
import main.security.AuthTokenGenerator;
import main.security.LogoutTokenGenerator;
import main.security.RefreshTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SearchService searchService;

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

            String loginToken = logoutTokenGenerator.getToken(user.getUsername(), 300);

            Cookie loggedInCookie = new Cookie("loggedIn", loginToken);
            loggedInCookie.setPath("/");
            loggedInCookie.setMaxAge(18000);

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

        model.addAttribute("userName", new FriendNameForm());
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

    @GetMapping("/search")
    ModelAndView search(@RequestParam(name = "name") String name, HttpServletResponse response, Model model) throws IOException {

        if (name.length() == 0) {
            response.sendRedirect("/pruebas/problems/4");
        }

        model.addAttribute("search", "searching : " + name);
        model.addAttribute("userList", searchService.getUserList(name));
        return new ModelAndView("search");
    }

    //---------------------------------------Friends Page---------------------------------------//
    /*
    @GetMapping("/friendsPage")
    ModelAndView friendsPage(Model model){

        model.addAttribute("userName", "friend");
        model.addAttribute("profilePic", "https://offloadmedia.feverup.com/madridsecreto.co/wp-content/uploads/2019/06/08103837/shutterstock_1051494194-1.jpg" );
        model.addAttribute("postList", userService.getPosts(getUserByName("user1").getName()));

        return new ModelAndView("friendsPage");
    }
    */
    @GetMapping("/friendsPage/{userName}")
    ModelAndView friendsPage(@PathVariable String userName, HttpServletResponse response,Model model){

        model.addAttribute("userName", userName.toString());
        model.addAttribute("profilePic", getUserByName(userName).getImage());
        model.addAttribute("postList", userService.getPosts(getUserByName(userName).getName()));

        return new ModelAndView("friendsPage");
    }


    @GetMapping("/collab")
    ModelAndView myCollab(Model model){
        return new ModelAndView("collab");
    }

}
