package main.rest.controller;

import main.application.service.EventService;
import main.domain.resource.EventoResource;
import main.domain.resource.MeetupResource;
import main.rest.forms.EventForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService service;


    // ORGANIZAR EVENTO
    //
    // crea un evento con los datos correspondientes

    @RequestMapping(value="/join/{id}", method=RequestMethod.POST)
    public ResponseEntity<?> joinEvent(@PathVariable String id) {

        try{
            Integer userID = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            MeetupResource meet= service.joinEvent(Integer.parseInt(id),userID);
            return meet != null ? ResponseEntity.ok(meet) : ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

    @RequestMapping(value="/leave/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<?> leaveEvent(@PathVariable String id) {
        try{
            Integer userID = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            MeetupResource meet= service.leaveEvent(Integer.parseInt(id),userID);
            return meet != null ? ResponseEntity.ok(meet) : ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createEvent(EventForm form) {
        try {

            Integer collabID = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
            form.setIdCollab(collabID);
            EventoResource evnt = service.upload(form);
            return evnt != null ? ResponseEntity.ok(evnt) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // MODIFICAR EVENTO
    //
    // modifica los datos del evento con el valor de id
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> modifyEvent(@PathVariable String id, EventForm form) {

        try {
            EventoResource evnt = service.modify(Integer.parseInt(id), form);
            return evnt != null ? ResponseEntity.ok(evnt) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ELIMINAR EVENTO
    //
    // elimina el evento con el valor de id completamente
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteEvent(@PathVariable String id) {

        return service.delete(Integer.parseInt(id));
    }
}
