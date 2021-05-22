package main.rest.controller;

import main.application.service.SponsorService;
import main.domain.resource.PatrocinioResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/sponsor")
public class SponsorController {

    @Autowired
    private SponsorService service;

    // OBTENER PATROCINIO
    //
    // devuelve un patrocinio especifico buscado por id
    // devuelve null si no existe
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PatrocinioResource> getSponsorship(@PathVariable String id) {

        PatrocinioResource sponsorship = service.getSponsorship(Integer.parseInt(id));
        return sponsorship != null ? ResponseEntity.ok(sponsorship) : ResponseEntity.notFound().build();
    }

    // CREAR PATROCINIO
    //
    // colaborador crea patrocinio del tipo/duracion elegido
    // devuelve el patrocinio (fecha de finalizacion)
    // se supone que ya se ha realizado el pago
    //
    // TIPOS DE PATROCINIO (duracion)
    //
    // type=1: 1 mes
    // type=2: 3 meses
    // type=3: 6 meses
    // type=4: 12 meses
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PatrocinioResource> createSponsorship(@RequestPart("id") String id, @RequestPart("type") String type) {

        PatrocinioResource sponsorship = service.obtain(Integer.parseInt(id), Integer.parseInt(type));
        return sponsorship != null ? ResponseEntity.ok(sponsorship) : ResponseEntity.notFound().build();
    }

    // MODIFICAR PATROCINIO
    //
    // colaborador modifica patrocinio del tipo/duracion elegido
    // devuelve el patrocinio (fecha de finalizacion)
    // se supone que ya se ha realizado el pago
    //
    // TIPOS DE PATROCINIO (duracion)
    //
    // type=1: 1 mes
    // type=2: 3 meses
    // type=3: 6 meses
    // type=4: 12 meses
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PatrocinioResource> modifySponsorship(@PathVariable String id, @RequestPart("type") String type) {

        PatrocinioResource sponsorship = service.modify(Integer.parseInt(id), Integer.parseInt(type));
        return sponsorship != null ? ResponseEntity.ok(sponsorship) : ResponseEntity.notFound().build();
    }
}
