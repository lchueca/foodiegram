package main.rest.controller;

import main.application.service.SearchService;
import main.domain.resource.PreviewColabJOINUser;
import main.domain.resource.PreviewPubliJOINUser;
import main.domain.resource.PreviewUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/search")
public class SearchRestController {

    @Autowired
    private SearchService service;

    // BUSQUEDA DE USUARIOS
    //
    // busca a usuarios cuyo name contenga el string "username" y devuelve una lista
    // con los nombres de usuario y su foto de perfil
    // si no encuentra devuelve una lista vacia
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<List<PreviewUsuario>> getUserList(@PathVariable String username) {

        List<PreviewUsuario> userList = service.getUserList(username);
        return userList != null ? ResponseEntity.ok(userList) : ResponseEntity.notFound().build();
    }

    // BUSQUEDA DE LOCALES
    //
    // busca a colaboradores cuyo name contenga el string "colabname" y devuelve una lista
    // con los datos de colaborador y su foto de perfil
    // si no encuentra devuelve una lista vacia
    @RequestMapping(value = "/colab/name/{colabname}", method = RequestMethod.GET)
    public ResponseEntity<List<PreviewColabJOINUser>> getColabListByName(@PathVariable String colabname) {

        List<PreviewColabJOINUser> colabList = service.getColabListByName(colabname);
        return colabList != null ? ResponseEntity.ok(colabList) : ResponseEntity.notFound().build();
    }

    // busca a colaboradores cuyo origin contenga el string "origin" y devuelve una lista
    // con los datos de colaborador y su foto de perfil
    // si no encuentra devuelve una lista vacia
    @RequestMapping(value = "/colab/origin/{origin}", method = RequestMethod.GET)
    public ResponseEntity<List<PreviewColabJOINUser>> getColabListByOrigin(@PathVariable String origin) {

        List<PreviewColabJOINUser> colabList = service.getColabListByOrigin(origin);
        return colabList != null ? ResponseEntity.ok(colabList) : ResponseEntity.notFound().build();
    }

    // busca a colaboradores cuyo type contenga el string "type" y devuelve una lista
    // con los datos de colaborador y su foto de perfil
    // si no encuentra devuelve una lista vacia
    @RequestMapping(value = "/colab/type/{type}", method = RequestMethod.GET)
    public ResponseEntity<List<PreviewColabJOINUser>> getColabListByType(@PathVariable String type) {

        List<PreviewColabJOINUser> colabList = service.getColabListByType(type);
        return colabList != null ? ResponseEntity.ok(colabList) : ResponseEntity.notFound().build();
    }

    // BUSQUEDA DE PUBLICACIONES
    //
    // busca las publicaciones que contengan un hashtag coincidente con "tag"y devuelve
    // una lista con los datos de las publicaciones (menos los ids) y el nombre del usuario que hizo
    // la publicacion
    // si no encuentra devuelve una lista vacia
    @RequestMapping(value = "/publi/{tag}", method = RequestMethod.GET)
    public ResponseEntity<List<PreviewPubliJOINUser>> getPubliListByTag(@PathVariable String tag) {

        List<PreviewPubliJOINUser> publiList = service.getPubliListByTag("#" + tag);
        return publiList != null ? ResponseEntity.ok(publiList) : ResponseEntity.notFound().build();
    }
}
