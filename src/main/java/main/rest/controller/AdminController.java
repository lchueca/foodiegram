package main.rest.controller;


import main.application.service.UserService;
import main.domain.resource.*;
import main.persistence.entity.RoleEnum;
import main.security.JWTokenGenerator;
import main.security.UserForm;
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

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String xD() {
        return "Heeyy!";
    }


}

