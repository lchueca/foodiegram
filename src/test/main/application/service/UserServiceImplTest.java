package main.application.service;

import lombok.var;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.UsuarioResource;
import main.domain.resource.ValoracionResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

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

        //Con un usuario existente, comprobamos que se devuelve una lista de post
        userService = new UserServiceImpl();
        String nombre = "Grogu";
        List<PreviewPublicacion> postUsuario =  userService.getPosts(nombre);
        Assertions.assertNotNull(postUsuario);

        //Le envio un usuario que no existe y tiene que devolver null;
        userService = new UserServiceImpl();
        String nombreNull = "Grogu";
        List<PreviewPublicacion> postUsuarioNull =  userService.getPosts(nombreNull);
        Assertions.assertNull(postUsuarioNull);
    }

    @Test
    void upload() {
        //Con un usuario existente, comprobamos que el usuario existe y se manda una fotografía
        userService = new UserServiceImpl();
        String nombre = "Grogu";
        String texto = "Que rico!";
        String localizacion = "Dagobah";

        // MultipartFile foto = (nombre,  );
        // PublicacionResource postUsuario =  userService.upload();
        //Assertions.assertNotNull(postUsuario);
    }

    @Test
    void getRatings() {
        //Con un usuario existente, comprobamos que se devuelve una lista de ratings
        userService = new UserServiceImpl();
        String nombre = "Grogu";
        List<ValoracionResource> rating =  userService.getRatings(nombre);
        Assertions.assertNotNull(rating);

        //Le envio un usuario que no existe y tiene que devolver null;
        userService = new UserServiceImpl();
        String nombreNull = "Grogu";
        List<ValoracionResource> ratingNull =  userService.getRatings(nombreNull);
        Assertions.assertNull(ratingNull);
    }

    @Test
    void register() {
        //Con un usuario existente, comprobamos que se devuelve una lista de ratings
        userService = new UserServiceImpl();
        String nombre = "Grogu";
        String contraseña = "MandoBestDad123";
        String email = "babyYodarules@gmail.es";

        UsuarioResource i =  userService.register(nombre, contraseña, email);
        Assertions.assertEquals("Grogu", i.getName());


        //Le envio un usuario cuyo nombre no es valido y tiene que devolver la excepcion;

        userService = new UserServiceImpl();
        String nombreNull = "Grogu El Individuo Mas Adorable Del Mundo";
        String mensaje = "The name is to long, please insert a name BELOW 20 characters";

        /*
        var expectedException =
            assertThrows(
                    UserServiceImpl -> {
                        throw new IllegalArgumentException(mensaje);
                    }
            );

        assertEquals(mensaje, expectedException.getMessage());
        */

    }

    @Test
    void verify() {
    }

    @Test
    void getMensajes() {
    }
}