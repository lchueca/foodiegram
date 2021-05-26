package main.application.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.Properties;
import java.util.regex.Matcher;


import main.domain.converter.*;
import main.domain.converter.MensajeConverter;
import main.domain.resource.*;
import main.persistence.entity.*;
import main.persistence.repository.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    /**
     * TEST GET USER BY NAME
     */

    @Mock
    RepoUsuario repoUsuarioMock;
    UsuarioConverter converterUserMock;


    @Test
    void getUserByNameTestGood() { //X

        //DEVUELVE UN TIPO USUARIO
        //Creamos un tipo usuario y solo rellenamos lo que nos interesa
        UsuarioResource usuario = new UsuarioResource();
        usuario.setName("Grogu");

        when(converterUserMock.convert(repoUsuarioMock.findByName("Grogu"))).thenReturn(usuario);
        assertEquals("Grogu", usuario.getName());

    }

    @Test
    void getUserByNameTestAMedias(){ //X

        UsuarioResource usuario = new UsuarioResource();
        usuario.setName("Grogu");

        when(converterUserMock.convert(repoUsuarioMock.findByName("Gro"))).thenReturn(usuario);
        assertEquals("Grogu", usuario.getName());

    }

    @Test
    void getUserByNameTestNull(){ //X

        UsuarioResource usuario = new UsuarioResource();
        usuario.setName("Grogu");

        //DEVUELVE UN NULL
        usuario = null;
        when(converterUserMock.convert(repoUsuarioMock.findByName("Aslan"))).thenReturn(usuario);
        assertNull(usuario);
    }



    /**
    * TEST GET POSTS
    */

    @Mock
    List<Publicacion> ListPubliMock;
    PreviewPublicacionConverter converterPreviewMock;
    List<PreviewPublicacion> ListPrePubliMock;
    RepoPublicacion repoPubliMock;

    @Test
    void getPostsTestGood() { //X

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        //DEVUELVE UNA LISTA
        when(usuarioMock == null).thenReturn(false);
        when(repoPubliMock.findByIduser(usuarioMock.getId())).thenReturn(ListPubliMock);
        when(ListPubliMock.stream().map(converterPreviewMock::convert).collect(Collectors.toList())).thenReturn(ListPrePubliMock);
        assertNotNull(ListPrePubliMock);

    }



    /**
     * TEST UPLOAD
     */

    @Mock
    Publicacion publiMock;
    PublicacionResource publiResoMock;
    PublicacionConverter converterPubliMock;
    Pattern imagePatMock;
    MultipartFile imageMock;
    Matcher matchMock;

    /*
    @Test
    void uploadTestGood() { //Test X

        Usuario usuarioMock = Mockito.mock(Usuario.class);
        String apacheMock = Mockito.mock(String.class);
        String nameMock = Mockito.mock(String.class);
        String addressMock = Mockito.mock(String.class);
        File fileMock = Mockito.mock(File.class);
        FileOutputStream streamMock = Mockito.mock(FileOutputStream.class);

        //DEVUELVE UNA PUBLICACION (Xayah)
        when(usuarioMock == null).thenReturn(false);
        when(imagePatMock.matcher(imageMock.getOriginalFilename())).thenReturn(matchMock);
        when(!matchMock.matches()).thenReturn(false);
        when(new Publicacion("Un baile conmigo termina con sangre", usuarioMock.getId(), "Jonia")).thenReturn(publiMock);
        when(repoPubliMock.save(publiMock)).thenReturn(publiMock);

        try {
            when(new File(apacheMock + "/" + usuarioMock.getId())).thenReturn(fileMock);
            when(fileMock.getAbsolutePath() + "/" + publiMock.getId() + "." + matchMock.group(1)).thenReturn(nameMock);
            when(new FileOutputStream(nameMock)).thenReturn(streamMock);
            when(String.format("%s/%s/%s.%s", apacheMock, usuarioMock.getId(), publiMock.getId(), matchMock.group(1))).thenReturn(addressMock);

        } catch (FileNotFoundException e) {

        }

        when(converterPubliMock.convert(publiMock)).thenReturn(publiResoMock);
        assertNotNull(publiResoMock);

    }

    @Test
    void uploadTestException(){ //X

        //EXCEPCION NO SE PUEDE GUARDAR LA IMAGEN
        Properties properties = Mockito.mock(Properties.class);
        Usuario usuarioMock = Mockito.mock(Usuario.class);

        when(usuarioMock == null).thenReturn(false);
        when(imagePatMock.matcher(imageMock.getOriginalFilename())).thenThrow(new IllegalArgumentException("Only jpeg and png images are supported."));
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> properties.get("Only jpeg and png images are supported."));
        assertEquals("Only jpeg and png images are supported.",exception.getMessage());

    }
    */


    /**
     * TEST GET RATINGS
     */

    @Mock
    RepoValoracion repoValMock;
    ValoracionConverter valConverterMock;
    List<Valoracion> valoracionMock;
    List<ValoracionResource> valoracionResoMock;


    @Test
    void getRatingsTestGood() { //X

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        when(repoUsuarioMock.findByName("Xayah")).thenReturn(usuarioMock);
        when(usuarioMock==null).thenReturn(false);
        when(repoValMock.findByIduser(usuarioMock.getId())).thenReturn(valoracionMock);
        when(valoracionMock.stream().map(valConverterMock::convert).collect(Collectors.toList())).thenReturn(valoracionResoMock);
        assertNotNull(valoracionResoMock);

    }



    /**
     * TEST REGISTER
     */

    @Mock
    UsuarioResource usuarioResourceMock;

    @Test
    void registerTestNombreUSuario() { //X

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        // Nombre de usuario
        when(usuarioMock.getName().length() >= 20).thenThrow(new IllegalArgumentException("The name is to long, please insert a name BELOW 20 characters"));
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> usuarioMock.getName().length());
        assertNotNull(exception.getMessage());

    }

    @Test
    void registerTestPassword(){ //X

        Usuario usuarioMock = Mockito.mock(Usuario.class);
        when(usuarioMock.getName().length() >= 20).thenReturn(false);
        when(usuarioMock.getPasswd().length()).thenThrow(new IllegalArgumentException("The PASSWORD is to long, please insert a password BELOW 20 characters"));
        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> usuarioMock.getPasswd().length());
        assertNotNull(exception1.getMessage());

    }

    @Test
    void registerTestMalEmail(){ //X

        Usuario usuarioMock = Mockito.mock(Usuario.class);
        when(usuarioMock.getName().length() >= 20).thenReturn(false);
        when(usuarioMock.getPasswd().length()>=256).thenReturn(false);
        when(!usuarioMock.getEmail().contains("@")).thenThrow(new IllegalArgumentException("The email introduces is NOT valid, please insert a valid e-mail"));
        Throwable exception2 = assertThrows(IllegalArgumentException.class, () -> usuarioMock.getEmail().contains("@"));
        assertNotNull(exception2.getMessage());

    }

    @Test
    void registerTestEmailRepe(){ //X

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        when(usuarioMock.getName().length() >= 20).thenReturn(false);
        when(usuarioMock.getPasswd().length()>=256).thenReturn(false);
        when(!usuarioMock.getEmail().contains("@")).thenReturn(false);
        when(repoUsuarioMock.findByEmail(usuarioMock.getEmail()) != null).thenThrow(new IllegalArgumentException("That e-mail is already registered"));
        Throwable exception3 = assertThrows(IllegalArgumentException.class, () -> repoUsuarioMock.findByEmail(usuarioMock.getEmail()));
        assertNotNull(exception3.getMessage());

    }

    @Test
    void registerTestTodoGood(){ //X

        Usuario usuarioMock = Mockito.mock(Usuario.class);
        Usuario newUserMock = Mockito.mock(Usuario.class);
        int tokenMock = Mockito.mock(int.class);
        Random randomMock = Mockito.mock(Random.class);
        RepoVerifytoken repoTokenMock = Mockito.mock(RepoVerifytoken.class);
        BCryptPasswordEncoder encoderMock = Mockito.mock(BCryptPasswordEncoder.class);
        Verifytoken verifytokenMock = Mockito.mock(Verifytoken.class);
        String stringMock = Mockito.mock(String.class);


        when(usuarioMock.getName().length() >= 20).thenReturn(false);
        when(usuarioMock.getPasswd().length()>=256).thenReturn(false);
        when(!usuarioMock.getEmail().contains("@")).thenReturn(false);
        when(repoUsuarioMock.findByEmail(usuarioMock.getEmail()) != null).thenReturn(false);
        when(randomMock.nextInt(100000000)).thenReturn(tokenMock);
        when(repoTokenMock.findByToken(tokenMock) != null).thenReturn(false);
        when(new BCryptPasswordEncoder()).thenReturn(encoderMock);
        when(new Usuario("Xayah", encoderMock.encode("Rakan"),null, "TortolitosRunaterra@gmail.com")).thenReturn(newUserMock);
        when(new Verifytoken("TortolitosRunaterra@gmail.com", tokenMock)).thenReturn(verifytokenMock);
        when("Enlace de verificación: " + stringMock + "/users/verify/" + tokenMock).thenReturn(stringMock);
        when("Confirmación de correo electrónico en foodiegram.").thenReturn(stringMock);

        when(converterUserMock.convert(usuarioMock)).thenReturn(usuarioResourceMock);
        assertNotNull(usuarioMock);

    }



    /**
     * TEST GET MENSAJES
     */

    @Mock
    List<MensajeResource> mensajeResoMock;
    MensajeConverter mensajeConvMock;
    List<Mensaje> mensajesMock;
    RepoMensaje repoMensMock;

    @Test
    void getMensajesTestGood() { //X

        Usuario usuarioMock = Mockito.mock(Usuario.class);
        when(repoUsuarioMock.findByName("Xayah")).thenReturn(usuarioMock);
        when(usuarioMock == null).thenReturn(false);
        when(repoMensMock.findByIduser1OrIduser2(usuarioMock.getId(), usuarioMock.getId())).thenReturn(mensajesMock);
        when(mensajesMock.stream().map(mensajeConvMock::convert).collect(Collectors.toList())).thenReturn(mensajeResoMock);
        assertNotNull(mensajeResoMock);

    }

}