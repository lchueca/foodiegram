package main.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.Properties;

import lombok.var;
import main.domain.converter.PublicacionConverter;
import main.domain.converter.UsuarioConverter;
import main.domain.converter.PreviewPublicacionConverter;

import main.domain.resource.UsuarioResource;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PublicacionResource;
import main.domain.resource.ValoracionResource;

import main.persistence.entity.Usuario;
import main.persistence.repository.RepoUsuario;
import main.persistence.entity.Publicacion;




import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;

    @Mock
    RepoUsuario repoUsuarioMock;
    UsuarioConverter converterUserMock;


    @Test
    void getUserByName() { //TEST X

        assertNotNull(repoUsuarioMock);

        //DEVUELVE UN TIPO USUARIO
        //Creamos un tipo usuario y solo rellenamos lo que nos interesa
        UsuarioResource usuario = new UsuarioResource();
        usuario.setName("Grogu");


        when(converterUserMock.convert(repoUsuarioMock.findByName("Grogu"))).thenReturn(usuario);
        assertEquals("Grogu", usuario.getName());


        when(converterUserMock.convert(repoUsuarioMock.findByName("Gro"))).thenReturn(usuario);
        assertEquals("Grogu", usuario.getName());


        //DEVUELVE UN NULL
        usuario = null;
        when(converterUserMock.convert(repoUsuarioMock.findByName("Aslan"))).thenReturn(usuario);
        assertNull(usuario);

    }


    @Mock
    List<Publicacion> ListPubliMock;
    PreviewPublicacionConverter converterPreviewMock;
    List<PreviewPublicacion> ListPrePubliMock;

    @Test
    void getPosts() { //Test X

        assertNotNull(ListPubliMock);

        //DEVUELVE UNA LISTA
        when(ListPubliMock.stream().map(converterPreviewMock::convert).collect(Collectors.toList())).thenReturn(ListPrePubliMock);
        assertNotNull(ListPrePubliMock);


        //DEVUELVE NULL
        ListPrePubliMock = null;
        when(ListPubliMock.stream().map(converterPreviewMock::convert).collect(Collectors.toList())).thenReturn(ListPrePubliMock);
        assertNull(ListPrePubliMock);

    }

    @Mock
    //repoUsuarioMock (Arriba)
    PublicacionResource publiResoMock;
    Pattern imagePAtMock;
    MultipartFile imageMock;
    PublicacionConverter converterPubliMock;
    Publicacion publiMock;

    @Test
    void upload() { //Test X
        assertNotNull(repoUsuarioMock);
        Usuario usuario = null;

        //DEVUELVE NULL
        when(repoUsuarioMock.findByName("Aslan")).thenReturn(usuario);
        assertNull(usuario);

        //EXCEPCION NO SE PUEDE GUARDAR LA IMAGEN
        Properties properties = Mockito.mock(Properties.class);
        when(imagePAtMock.matcher(imageMock.getOriginalFilename())).thenThrow(new IllegalArgumentException("Only jpeg and png images are supported."));
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> properties.get("Only jpeg and png images are supported."));
        assertEquals("Only jpeg and png images are supported.",exception.getMessage());

        //DEVUELVE UNA PUBLICACION
        when(converterPubliMock.convert(publiMock)).thenReturn(publiResoMock);
        assertNotNull(publiResoMock);

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