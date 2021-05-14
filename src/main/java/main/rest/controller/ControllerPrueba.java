package main.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerPrueba {

    @GetMapping(value = "/prueba")
    String people(Model model){
        model.addAttribute("people", "Hello Bitches with Thymeleaf" );
        return "people";
    }

}
