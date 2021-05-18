package main.application.service;

import main.domain.converter.ComentarioConverter;
import main.domain.resource.ComentarioResource;
import main.persistence.entity.Comentario;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoComentario;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ComentarioServiceImplTest {

    @Mock
    RepoComentario repoCommentMock;
    Comentario commentMock;
    ComentarioConverter commentConverterMock;
    ComentarioResource comentarioResoMock;

    @Test
    void editComentario() { //X

        assertNotNull(repoCommentMock);
        Comentario commentMock = null;

        //DEVUELVE NULL
        when(repoCommentMock.findOne(1265)).thenReturn(commentMock);
        assertNull(commentMock);

        //Devuelve un ComentarioResource  con el mismo comID  y texto modificado
        when(commentConverterMock.convert(commentMock)).thenReturn(comentarioResoMock);
        assertNotNull(comentarioResoMock);

        //Si el texto modificado es nulo salta la expection

        Comentario commentMocki = Mockito.mock(Comentario.class);


        when(commentMocki.getText()).thenThrow(new IllegalArgumentException());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> commentMocki.getText());

        assertNotNull(exception.getMessage());


    }

    @Test
    void deleteComentario() { //X
        assertNotNull(repoCommentMock);
        Comentario commentMock = null;

        //DEVUELVE NULL
        when(repoCommentMock.findOne(1265)).thenReturn(commentMock);
        assertNull(commentMock);

        //Elimina el mensaje
        when(commentConverterMock.convert(commentMock)).thenReturn(comentarioResoMock);
        assertNotNull(comentarioResoMock);

    }
}