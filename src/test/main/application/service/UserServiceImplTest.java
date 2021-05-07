package main.application.service;

import main.domain.resource.UsuarioResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;


    @Test
    void getUserByName() {

        //Comprueba que devuelve el mismo nombre
        userService = new UserServiceImpl();
        String nombre = "Grogu";
        UsuarioResource usuario =  userService.getUserByName(nombre);
        Assertions.assertEquals("Grogu", usuario.getName());


        //Le envio uno que no existe y tiene que devolver null;
        userService = new UserServiceImpl();
        String nombrenull = "Aslan";
        UsuarioResource usuarioNull = userService.getUserByName(nombrenull);
        Assertions.assertNull(usuarioNull);

    }

    @Test
    void getPosts() {


    }

    @Test
    void upload() {
    }

    @Test
    void getRatings() {
    }

    @Test
    void register() {
    }

    @Test
    void verify() {
    }

    @Test
    void getMensajes() {
    }
}