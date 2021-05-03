package main.rest.controller;

import main.application.service.manageAccountService.ManageFriends;
import main.application.service.manageAccountService.ManageInfo;
import main.application.service.manageAccountService.Unsubscribe;
import main.application.service.manageAccountService.ViewImages;
import main.domain.resource.AmigoResource;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/manage_account/{id}")
public class ControllerManageAccount {

    @Autowired
    ManageFriends manageFriends;
    ManageInfo manageInfo;
    Unsubscribe unsubscribeService;
    ViewImages viewImagesService;

    //--MANAGE FRIENDS--

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> add(@PathVariable Integer id, @RequestPart(value = "name", required = true) String name){

        AmigoResource friend = manageFriends.addFriend(id, name);
        return friend != null ? ResponseEntity.ok(friend) : ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> remove(@PathVariable Integer id, @RequestPart(value = "name", required = true) String name){

        AmigoResource friend = manageFriends.removeFriend(id, name);
        return friend != null ? ResponseEntity.ok(friend) : ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PreviewPublicacion>> viewImagesofFriend(@PathVariable Integer id, @RequestPart(value = "name", required = true) String name){

        List<PreviewPublicacion> publicacionesAmigo = manageFriends.viewPostOfFriend(id, name);
        return publicacionesAmigo != null ? ResponseEntity.ok(publicacionesAmigo) : ResponseEntity.notFound().build();
    }
    //-------------------------------------------------------------------------------------------------------------------------

    //--MANAGE INFO--


    @RequestMapping(value = "/newName", method = RequestMethod.POST)
    public ResponseEntity<?> changeName(@PathVariable Integer id, @RequestPart(value = "newName", required = true) String newName){
        UsuarioResource user = manageInfo.changeName(id, newName);
        return user!= null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @RequestMapping(value ="/newPasswd", method = RequestMethod.POST)
    public ResponseEntity<?> changePasswd(@PathVariable Integer id, @RequestPart(value = "newPasswd", required = true) String newPasswd){
        UsuarioResource user = manageInfo.changePasswd(id, newPasswd);
        return user!= null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/newEmail", method = RequestMethod.POST)
    public ResponseEntity<?> changeEmail(@PathVariable Integer id, @RequestPart(value = "newEmail", required = true) String newEmail){
        UsuarioResource user = manageInfo.changeEmail(id, newEmail);
        return user!= null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/newPic", method = RequestMethod.POST)
    public ResponseEntity<?> changeProfilePic(@PathVariable Integer id, @RequestPart(value = "newPic", required = true) String newPic){
        UsuarioResource user = manageInfo.changeProfilePicture(id, newPic);
        return user!= null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    //-------------------------------------------------------------------------------------------------------------------------

    //--UNSUBSCRIBE--

    @RequestMapping(value  = "/unsubscribe",method = RequestMethod.DELETE)
    public ResponseEntity<?> unsubscribe(@PathVariable Integer id){
        UsuarioResource user = unsubscribeService.unsubscribe(id);
        return user!= null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    //-------------------------------------------------------------------------------------------------------------------------

    //--VIEW MY IMAGES--

    @RequestMapping(value = "/viewImage", method = RequestMethod.GET)
    public ResponseEntity<List<PreviewPublicacion>> viewMyImages(@PathVariable Integer id){
        List<PreviewPublicacion> _listPost = viewImagesService.viewPost(id);
        return _listPost != null ? ResponseEntity.ok(_listPost) : ResponseEntity.notFound().build();
    }
    //-------------------------------------------------------------------------------------------------------------------------

}
