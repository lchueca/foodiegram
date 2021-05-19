package main.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.Properties;

import lombok.var;
import main.domain.converter.*;
import main.domain.converter.MensajeConverter;

import main.domain.resource.*;

import main.persistence.entity.*;
import main.persistence.repository.RepoMensaje;
import main.persistence.repository.RepoUsuario;
import main.persistence.repository.RepoValoracion;
import main.persistence.repository.RepoVerifytoken;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


class UserServiceImplTest {


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

    @Mock
    RepoValoracion repoValMock;
    ValoracionConverter valConverterMock;
    List<Valoracion> valoracionMock;
    List<ValoracionResource> valoracionResoMock;

    @Test
    void getRatings() { //X

        assertNotNull(repoUsuarioMock);
        Usuario usuario = null;

        //DEVUELVE NULL
        when(repoUsuarioMock.findByName("Aslan")).thenReturn(usuario);
        assertNull(usuario);

        //Devuelve la lista de ratings
        when(valoracionMock.stream().map(valConverterMock::convert).collect(Collectors.toList())).thenReturn(valoracionResoMock);
        assertNotNull(valoracionResoMock);

    }
    @Mock
    UsuarioResource usuarioMock;


    @Test
    void register() {

        Usuario usuario = Mockito.mock(Usuario.class);

        // Nombre de usuario
        when(usuario.getName().length()).thenThrow(new IllegalArgumentException("The name is to long, please insert a name BELOW 20 characters"));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> usuario.getName().length());

        assertNotNull(exception.getMessage());

        //Contraseña
        when(usuario.getPasswd().length()).thenThrow(new IllegalArgumentException("The PASSWORD is to long, please insert a password BELOW 20 characters"));

        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> usuario.getPasswd().length());

        assertNotNull(exception1.getMessage());

        //Email inadecuado

        when(usuario.getEmail().contains("@")).thenThrow(new IllegalArgumentException("The email introduces is NOT valid, please insert a valid e-mail"));

        Throwable exception2 = assertThrows(IllegalArgumentException.class, () -> usuario.getEmail().contains("@"));

        assertNotNull(exception2.getMessage());

        // Email repe

        when(repoUsuarioMock.findByEmail(usuario.getEmail())).thenThrow(new IllegalArgumentException("That e-mail is already registered"));

        Throwable exception3 = assertThrows(IllegalArgumentException.class, () -> repoUsuarioMock.findByEmail(usuario.getEmail()));

        assertNotNull(exception3.getMessage());

        //Registro correcto
        when(converterUserMock.convert(usuario)).thenReturn(usuarioMock);
        assertNotNull(usuarioMock);
    }

    @Mock
    List<MensajeResource> mensajeResoMock;
    MensajeConverter mensajeConvMock;
    List<Mensaje> mensajesMock;

    @Test
    void getMensajes() {

        assertNotNull(repoUsuarioMock);
        Usuario usuario = null;

        //DEVUELVE UNA LISTA
        when(mensajesMock.stream().map(mensajeConvMock::convert).collect(Collectors.toList())).thenReturn(mensajeResoMock);
        assertNotNull(mensajeResoMock);


        //DEVUELVE NULL
        mensajeResoMock = null;
        when(mensajesMock.stream().map(mensajeConvMock::convert).collect(Collectors.toList())).thenReturn(mensajeResoMock);
        assertNull(mensajeResoMock);
    }
}