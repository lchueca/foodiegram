package main.application.service;

import main.domain.converter.MensajeConverter;
import main.domain.resource.MensajeResource;
import main.persistence.entity.Mensaje;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoMensaje;

import main.persistence.repository.RepoUsuario;
import org.junit.jupiter.api.Test;;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class MensajeServiceImplTest {

    @Mock
    MensajeConverter MensConvMock;
    RepoMensaje repoMensMock;
    MensajeResource resoMensMock;
    Integer mensIDMock;

    @Test
    void getMensaje() { //X

        //DEVUELVE MensajeResource
        when(MensConvMock.convert((repoMensMock).findOne(mensIDMock))).thenReturn(resoMensMock);
        assertNotNull(resoMensMock);

        //DEVUELVE NULL
        resoMensMock = null;
        when(MensConvMock.convert((repoMensMock).findOne(mensIDMock))).thenReturn(resoMensMock);
        assertNull(resoMensMock);

    }

    @Mock
    //mensIDMock
    Mensaje mensMock;

    @Test
    void deleteMensaje() { //REVISAR


        //DEVUELVE MENSAJE

        when(repoMensMock.findOne(mensIDMock)).thenReturn(mensMock);
        when(mensMock != null).thenReturn(false);
        //when(repoMensMock.delete().thenReturn();
        assertNotNull(resoMensMock);


        //DEVUELVE NULL
        mensMock=null;
        when(mensMock != null).thenReturn(true);
        when(repoMensMock.findOne(mensIDMock)).thenReturn(mensMock);
        when(MensConvMock.convert(mensMock)).thenReturn(resoMensMock);
        assertNull(resoMensMock);
    }

    @Mock
    RepoUsuario repoUsuarioMock;
    Usuario usuarioMock;
    Usuario usuarioMock2;
    String mensajeMock;

    @Test
    void setMensaje() {

        //DEVUELVE Mensajeresource
        when(repoUsuarioMock.findByName("Grogu")).thenReturn(usuarioMock);
        when(repoUsuarioMock.findByName("Mando")).thenReturn(usuarioMock2);
        when(usuarioMock == null || usuarioMock2 == null).thenReturn(false);
        when(new Mensaje(usuarioMock.getId(),usuarioMock2.getId(),mensajeMock)).thenReturn(mensMock);
        when(repoMensMock.save(mensMock)).thenReturn(null);
        when(MensConvMock.convert(mensMock));
        assertNotNull(mensMock);


        //DEVUELVE NULL
        usuarioMock2 = null;
        when(repoUsuarioMock.findByName("Grogu")).thenReturn(usuarioMock);
        when(repoUsuarioMock.findByName("Mando")).thenReturn(usuarioMock2);
        when(usuarioMock == null || usuarioMock2 == null).thenThrow(new IllegalArgumentException("Both users should exist."));
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> usuarioMock2.getId());
        assertNotNull(exception.getMessage());

    }
}