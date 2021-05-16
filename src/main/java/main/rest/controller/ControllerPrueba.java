package main.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value ="/pruebas")
public class ControllerPrueba {




    @GetMapping()
    public ModelAndView people(Model model){

        ModelAndView modelAndView = new ModelAndView("people");

        model.addAttribute("people", "Hello Bitches with Thymeleaf" );
        return modelAndView;
    }

}
