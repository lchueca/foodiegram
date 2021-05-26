package main.rest.controller;

import main.application.service.EventService;
import main.domain.resource.EventoResource;
import main.rest.forms.EventForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService service;

    // ORGANIZAR EVENTO
    //
    // crea un evento con los datos correspondientes

    @RequestMapping(value="/join", method=RequestMethod.GET)
    public String joinEvent() {
        return "TODO";
    }

    @RequestMapping(value="/leave", method=RequestMethod.GET)
    public String leaveEvent() {
        return "TODO";
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
