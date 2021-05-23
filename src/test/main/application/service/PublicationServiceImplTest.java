package main.application.service;

import main.domain.converter.PublicacionConverter;
import main.domain.converter.ValoracionConverter;
import main.domain.resource.PublicacionResource;
import main.domain.resource.ValoracionResource;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Valoracion;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoValoracion;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

import java.util.List;
import java.lang.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
    void editPost() { //REVISAR
        String textN = "This belongs in a museum";
        String locN = null;

        when(textN == null || locN == null).thenThrow(new IllegalArgumentException("Text or loc should be not null"));
        //Throwable exception = assertThrows(IllegalArgumentException.class, () -> ;
        //assertNotNull(exception.getMessage());


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
    void getRatings() { //X
        when(repoPubliMock.findOne(117)).thenReturn(publiMock);
        when(publiMock == null).thenReturn(false);
        when(repoValoracionMock.findByIdpubli(117)).thenReturn(valoracionesMock);
        when(valoracionesMock.stream().map(ValoracionConverterMock::convert).collect(Collectors.toList())).thenReturn(getRatingsMock);
        assertNotNull(getRatingsMock);
    }

    @Test
    void setRating() {
    }

    @Test
    void getRating() {
    }

    @Test
    void deleteRating() {
    }

    @Test
    void getComments() {
    }

    @Test
    void setComment() {
    }
}