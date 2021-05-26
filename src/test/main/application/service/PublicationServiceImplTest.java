package main.application.service;

import main.domain.converter.ComentarioConverter;
import main.domain.converter.PublicacionConverter;
import main.domain.converter.ValoracionConverter;
import main.domain.resource.ComentarioResource;
import main.domain.resource.PublicacionResource;
import main.domain.resource.ValoracionResource;

import main.persistence.IDs.IDvaloracion;
import main.persistence.entity.Comentario;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.entity.Valoracion;
import main.persistence.repository.RepoComentario;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import main.persistence.repository.RepoValoracion;

import main.rest.forms.CommentForm;
import main.rest.forms.RatingForm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.lang.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PublicationServiceImplTest {

    @Mock
    PublicacionResource getPostMock;
    PublicacionConverter converterPubliMock;
    RepoPublicacion repoPubliMock;

    @Test
    void getPost() { //X
        when(converterPubliMock.convert(repoPubliMock.findOne(117))).thenReturn(getPostMock);
        assertNotNull(getPostMock);
    }

    @Mock
    PublicacionResource editPostMock;
    Publicacion publiMock;

    @Test
    void editPostOk() { //REVISAR

        String text = "This belongs in a museum";
        String loc = "Piltover";

        when(text == null || loc == null).thenReturn(false);
        when(repoPubliMock.findOne(117)).thenReturn(publiMock);
        when(publiMock != null).thenReturn(true);
        when(text != null).thenReturn(true);
        when(loc != null).thenReturn(true);
        when(converterPubliMock.convert(publiMock)).thenReturn(editPostMock);
        assertNotNull(editPostMock);
    }

    @Test
    void editPostException(){
        String textN = "This belongs in a museum";
        String locN = null;
        when(textN == null || locN == null).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> { });

    }
    @Mock
    PublicacionResource deletePostMock;

    @Test
    void deletePost() { //X
        when(repoPubliMock.findOne(117)).thenReturn(publiMock);
        when(publiMock != null).thenReturn(true);
        when(converterPubliMock.convert(publiMock)).thenReturn(deletePostMock);
        assertNotNull(deletePostMock);
    }

    @Mock
    List<ValoracionResource> getRatingsMock;
    List<Valoracion> valoracionesMock;
    RepoValoracion repoValoracionMock;
    ValoracionConverter ValoracionConverterMock;

    @Test
    void getRatingsOk() { //X
        when(repoPubliMock.findOne(117)).thenReturn(publiMock);
        when(publiMock == null).thenReturn(false);
        when(repoValoracionMock.findByIdpubli(117)).thenReturn(valoracionesMock);
        when(valoracionesMock.stream().map(ValoracionConverterMock::convert).collect(Collectors.toList())).thenReturn(getRatingsMock);
        assertNotNull(getRatingsMock);
    }

    @Test
    void getRatingNull(){ //REVISAR
        when(repoPubliMock.findOne(117)).thenReturn(publiMock);
        when(publiMock == null).thenReturn(true);
        assertNull(getRatingsMock);
    }

    @Mock
    ValoracionResource valoracionResourceMock;
    RatingForm formMock;
    Valoracion valMock;

    @Test
    void setRatingOk() {
        when(new Valoracion(formMock.getPubID(), formMock.getUserID(), formMock.getScore())).thenReturn(valMock);
        when(ValoracionConverterMock.convert(valMock)).thenReturn(valoracionResourceMock);
        assertNotNull(valoracionResourceMock);
    }

    @Test
    void setRatingException(){
        when(formMock.getScore() < 0 || formMock.getScore() > 5).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> { });

    }

    @Mock
    RepoUsuario repoUsuarioMock;
    Usuario usuarioMock;

    @Test
    void getRatingValOk() { //X
        String user = "Grogu";
        Integer id = 1256;
        when(repoUsuarioMock.findByName(user)).thenReturn(usuarioMock);
        when(usuarioMock == null).thenReturn(false);
        when(ValoracionConverterMock.convert(repoValoracionMock.findOne(new IDvaloracion(id, usuarioMock.getId())))).thenReturn(valoracionResourceMock);
        assertNotNull(valoracionResourceMock);
    }

    @Test
    void getRatingValNull() { //X
        String user = null;
        Integer id = 1256;
        when(repoUsuarioMock.findByName(user)).thenReturn(usuarioMock);
        when(usuarioMock == null).thenReturn(true);
        assertNull(valoracionResourceMock);

    }

    @Test
    void deleteRatingValOk() { //X
        when(repoValoracionMock.findOne(new IDvaloracion(formMock.getPubID(), formMock.getUserID()))).thenReturn(valMock);
        when(valMock != null).thenReturn(true);
        when(ValoracionConverterMock.convert(valMock)).thenReturn(valoracionResourceMock);
        assertNotNull(valoracionResourceMock);
    }

    @Test
    void deleteRatingValNull(){ //X
        when(repoValoracionMock.findOne(new IDvaloracion(formMock.getPubID(), formMock.getUserID()))).thenReturn(valMock);
        when(valMock != null).thenReturn(false);
        when(ValoracionConverterMock.convert(valMock)).thenReturn(valoracionResourceMock);
        assertNull(valoracionResourceMock);

    }

    @Mock
    List<ComentarioResource> comentarioResourcesMock;
    List<Comentario> comentariosMock;
    RepoComentario repoComenMock;
    ComentarioConverter converterComMock;

    @Test
    void getCommentsOkiDocky() { //X
        Integer pubID = 123;
        when(repoPubliMock.findOne(pubID)).thenReturn(publiMock);
        when(publiMock == null).thenReturn(false);
        when(repoComenMock.findByIdpubli(pubID)).thenReturn(comentariosMock);
        when(comentariosMock.stream().map(converterComMock::convert).collect(Collectors.toList())).thenReturn(comentarioResourcesMock);
        assertNotNull(comentarioResourcesMock);


    }

    @Test
    void getCommentsNull(){ //X
        Integer pubID = 123;
        when(repoPubliMock.findOne(pubID)).thenReturn(publiMock);
        when(publiMock == null).thenReturn(true);
        assertNull(comentarioResourcesMock);

    }

    @Mock
    ComentarioResource ComentarioResourceMock;
    Comentario comentarioMock;
    CommentForm commentFormMock;

    @Test
    void setCommentOk() { //X
        when(commentFormMock.getText() == null || commentFormMock.getText().length() == 0).thenReturn(false);
        when(new Comentario(commentFormMock.getPubID(), commentFormMock.getUserID(), commentFormMock.getText())).thenReturn(comentarioMock);
        when(converterComMock.convert(comentarioMock)).thenReturn(ComentarioResourceMock);
        assertNotNull(ComentarioResourceMock);
    }

    @Test
    void setCommentNull(){ //X
        when(commentFormMock.getText() == null || commentFormMock.getText().length() == 0).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //String.format("Text must be not null");
        });

    }
}