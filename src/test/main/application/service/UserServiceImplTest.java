package main.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import main.domain.converter.*;
import main.domain.resource.*;

import main.persistence.entity.*;
import main.persistence.repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)

class UserServiceImplTest {

    /**
     * TEST GET USER BY NAME
     */

    @Mock
    RepoUsuario repoUsuarioMock;
    UsuarioConverter converterUserMock;
    Usuario usuarioMock;

    @Test
    void getUserByNameTestGood() {

        //DEVUELVE UN TIPO USUARIO
        //Creamos un tipo usuario y solo rellenamos lo que nos interesa
        UsuarioResource usuario = new UsuarioResource();
        usuario.setName("Grogu");

        when(converterUserMock.convert(repoUsuarioMock.findByName("Grogu"))).thenReturn(usuario);
        assertEquals("Grogu", usuario.getName());

    }


    @Test
    void getUserByNameTestAMedias(){

        UsuarioResource usuario = new UsuarioResource();
        usuario.setName("Grogu");

        when(converterUserMock.convert(repoUsuarioMock.findByName("Gro"))).thenReturn(usuario);
        assertEquals("Grogu", usuario.getName());

    }

    @Test
    void getUserByNameTestNull(){

        UsuarioResource usuario = new UsuarioResource();
        usuario = null;
        //DEVUELVE UN NULL
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
    void getPostsTestGood() {

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        //DEVUELVE UNA LISTA
        when(usuarioMock == null).thenReturn(false);
        when(repoPubliMock.findByIduserOrderByIdDesc(usuarioMock.getId())).thenReturn(ListPubliMock);
        when(ListPubliMock.stream().map(converterPreviewMock::convert).collect(Collectors.toList())).thenReturn(ListPrePubliMock);
        assertNotNull(ListPrePubliMock);

    }

    @Test
    void getPostsTestNull() {

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        //DEVUELVE UNA LISTA Null
        when(usuarioMock == null).thenReturn(true);
        assertNull(ListPrePubliMock);

    }

    /**
     * TEST GET RATINGS
     */

    @Mock
    RepoValoracion repoValMock;
    ValoracionConverter valConverterMock;
    List<Valoracion> valoracionMock;
    List<ValoracionResource> valoracionResoMock;

    @Test
    void getRatingsTestGood() {

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        when(repoUsuarioMock.findByName("Xayah")).thenReturn(usuarioMock);
        when(usuarioMock==null).thenReturn(false);
        when(repoValMock.findByIduser(usuarioMock.getId())).thenReturn(valoracionMock);
        when(valoracionMock.stream().map(valConverterMock::convert).collect(Collectors.toList())).thenReturn(valoracionResoMock);
        assertNotNull(valoracionResoMock);

    }

    @Test
    void getRatingsTestNull() {

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        when(repoUsuarioMock.findByName("Xayah")).thenReturn(usuarioMock);
        when(usuarioMock==null).thenReturn(true);
        assertNull(valoracionResoMock);

    }

    /**
     * TEST REGISTER
     */

    @Mock
    UsuarioResource usuarioResourceMock;

    @Test
    void registerTestNombreUSuario() {

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        // Nombre de usuario
        when(usuarioMock.getName().length() >= 20).thenThrow(new IllegalArgumentException("The name is to long, please insert a name BELOW 20 characters"));
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> usuarioMock.getName().length());
        assertNotNull(exception.getMessage());

    }

    @Test
    void registerTestPassword(){

        Usuario usuarioMock = Mockito.mock(Usuario.class);
        when(usuarioMock.getName().length() >= 20).thenReturn(false);
        when(usuarioMock.getPasswd().length()).thenThrow(new IllegalArgumentException("The PASSWORD is to long, please insert a password BELOW 20 characters"));
        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> usuarioMock.getPasswd().length());
        assertNotNull(exception1.getMessage());

    }

    @Test
    void registerTestMalEmail(){

        Usuario usuarioMock = Mockito.mock(Usuario.class);
        when(usuarioMock.getName().length() >= 20).thenReturn(false);
        when(usuarioMock.getPasswd().length()>=256).thenReturn(false);
        when(!usuarioMock.getEmail().contains("@")).thenThrow(new IllegalArgumentException("The email introduces is NOT valid, please insert a valid e-mail"));
        Throwable exception2 = assertThrows(IllegalArgumentException.class, () -> usuarioMock.getEmail().contains("@"));
        assertNotNull(exception2.getMessage());

    }

    @Test
    void registerTestEmailRepe(){

        Usuario usuarioMock = Mockito.mock(Usuario.class);

        when(usuarioMock.getName().length() >= 20).thenReturn(false);
        when(usuarioMock.getPasswd().length()>=256).thenReturn(false);
        when(!usuarioMock.getEmail().contains("@")).thenReturn(false);
        when(repoUsuarioMock.findByEmail(usuarioMock.getEmail()) != null).thenThrow(new IllegalArgumentException("That e-mail is already registered"));
        Throwable exception3 = assertThrows(IllegalArgumentException.class, () -> repoUsuarioMock.findByEmail(usuarioMock.getEmail()));
        assertNotNull(exception3.getMessage());

    }

    @Test
    void registerTestTodoGood(){

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
     * TEST VERIFY
     */

    @Mock
    Verifytoken verifytokenMock;
    Integer tokenMock;
    RepoVerifytoken repoVerifytokenMock;

    @Test
    void verifyTestOK(){

        when(repoVerifytokenMock.findByToken(tokenMock)).thenReturn(verifytokenMock);
        when(verifytokenMock == null).thenReturn(false);
        when(verifytokenMock.getExpiredate().before(new Date())).thenReturn(false);
        when(repoUsuarioMock.findByEmail(verifytokenMock.getEmail())).thenReturn(usuarioMock);
        when(converterUserMock.convert(usuarioMock)).thenReturn(usuarioResourceMock);
        assertNotNull(usuarioResourceMock);

    }

    @Test
    void verifyTestNullToken(){

        when(repoVerifytokenMock.findByToken(tokenMock)).thenReturn(verifytokenMock);
        when(verifytokenMock == null).thenReturn(true);
        assertNull(usuarioResourceMock);

    }

    @Test
    void verifyTestExpired(){
        when(repoVerifytokenMock.findByToken(tokenMock)).thenReturn(verifytokenMock);
        when(verifytokenMock == null).thenReturn(false);
        when(verifytokenMock.getExpiredate().before(new Date())).thenReturn(true);
        assertNull(usuarioResourceMock);
    }

    /**
     * TEST UNBAN USER
     */

    @Mock
    Integer idMock;
    String userMock;
    Usuario_baneado bannedUserMock;
    RepoUsuario_baneado repoUsuario_baneadoMock;
    Usuario_baneadoResource usuario_baneadoResourceMock;

    @Test
    void unbanUserTestOk(){

        //Devuelve un NULL
        try{
            when(repoUsuarioMock.findByName(userMock)).thenReturn(usuarioMock);
            when(usuarioMock.getId()).thenReturn(idMock);
        }catch (Exception e){ //Si salta la exception la coge
            e.printStackTrace();
        }

        when(repoUsuario_baneadoMock.findOne(idMock)).thenReturn(bannedUserMock);
        assertNull(usuario_baneadoResourceMock);

    }

    /**
     * TEST DELETE USER
     */

    @Test
    void deleteUserTestOk(){

        //Devuelve NULL
        when(repoUsuarioMock.findByName(userMock)).thenReturn(usuarioMock);
        assertNull(usuarioResourceMock);

    }

    /**
     * TEST GET BANNED USER LIST
     */

    @Mock
    List<Usuario_baneado> listabaneado;
    List<Usuario_baneadoResource> listUserBanUserMock;
    Usuario_baneadoConverter userBanConverterMock;

    @Test
    void getBannedUserListTestOk(){

        when(repoUsuario_baneadoMock.findAll()).thenReturn(listabaneado);
        when(listabaneado.stream().map(userBanConverterMock::convert).collect(Collectors.toList())).thenReturn(listUserBanUserMock);
        assertNotNull(listUserBanUserMock);

    }

}