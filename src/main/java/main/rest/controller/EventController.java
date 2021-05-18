package main.rest.controller;

import main.application.service.EventService;
import main.domain.resource.EventoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService service;

    // ORGANIZAR EVENTO
    //
    // crea un evento con los datos correspondientes
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createEvent(@RequestPart("id") String idcolab, @RequestPart("text") String text,
                                         @RequestPart(value = "image", required = false) MultipartFile image,
                                         @RequestPart("date") String date) {
        try {
            EventoResource evnt = service.upload(Integer.parseInt(idcolab), text, image, date);
            return evnt != null ? ResponseEntity.ok(evnt) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // MODIFICAR EVENTO
    //
    // modifica los datos del evento con el valor de id
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> modifyEvent(@PathVariable String id, @RequestPart(value = "text", required = false) String text,
                                         @RequestPart(value = "image", required = false) MultipartFile image,
                                         @RequestPart(value = "date", required = false) String date) {

        try {
            EventoResource evnt = service.modify(Integer.parseInt(id), text, image, date);
            return evnt != null ? ResponseEntity.ok(evnt) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ELIMINAR EVENTO
    //
    // elimina el evento con el valor de id completamente
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public boolean deleteEvent(@PathVariable String id) {

        return service.delete(Integer.parseInt(id));
    }
}
