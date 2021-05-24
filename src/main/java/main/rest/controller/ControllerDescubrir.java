package main.rest.controller;


import main.application.service.DiscoverService;
import main.application.service.MensajeService;
import main.domain.resource.MensajeResource;
import main.domain.resource.PublicacionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

import javax.naming.NoPermissionException;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/discover")
public class ControllerDescubrir {

    @Autowired
    private DiscoverService service;

    @RequestMapping(value="/byFriends",method = RequestMethod.GET)
    public ResponseEntity<?> discoverByFriends(){

        try{
            Integer userid=Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            List<PublicacionResource> pub = service.discoverByAmigo(userid);
            return pub != null ? ResponseEntity.ok(pub) : ResponseEntity.notFound().build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(value="/byPopularity",method = RequestMethod.GET)
    public ResponseEntity<?> discoverByPopularity(){

        try{

            List<PublicacionResource> pub = service.discoverByPopularity();
            return pub != null ? ResponseEntity.ok(pub) : ResponseEntity.notFound().build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}



