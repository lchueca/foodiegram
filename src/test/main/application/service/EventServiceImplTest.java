package main.application.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import main.domain.resource.EventoResource;
import main.persistence.entity.Evento;
import main.persistence.repository.RepoEvento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.swing.event.ListDataEvent;
import java.util.List;


class EventServiceImplTest {

    /**
     * TEST GET EVENT LIST BY ID COLAB
     */


    @Mock
    RepoEvento repoEventoMock;
    List<EventoResource>  ListEventResourceMock;
    List<Evento> ListEventoMock;

    @Test
    void getEventListByIdcolab() {

        Integer idcolab = Mockito.mock(Integer.class);

        when(repoEventoMock.findByIdcolab(idcolab)).thenReturn(ListEventoMock);

        assertNotNull(ListEventResourceMock);

    }


    /**
     * TEST UPLOAD
     */

    @Test
    void upload() {
    }


    /**
     * TEST MODIFY
     */


    @Test
    void modify() {
    }


    /**
     * TEST DELETE
     */

    @Test
    void delete() {

        
    }
}