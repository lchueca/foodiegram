package main.rest.controller;

import main.application.service.ColaboradorService;
import main.domain.resource.ColaboradorResource;
import main.rest.forms.CollaborateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/colab")
public class ControllerColaborador {

    @Autowired
    private ColaboradorService service;

    @RequestMapping(value="/upgrade",method = RequestMethod.POST)
    public ResponseEntity<?> upgrade(CollaborateForm form){

        try{
            Integer userid=Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            ColaboradorResource colab= service.upgradeUser(userid,form);
            return colab != null ? ResponseEntity.ok(colab) : ResponseEntity.notFound().build();
        }

        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


}
