package main.application.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import main.domain.converter.PublicacionConverter;
import main.domain.resource.PreviewPublicacion;
import main.persistence.entity.Publicacion;
import main.persistence.repository.RepoPublicacion;


import main.persistence.repository.RepoUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


class DiscoverServiceImplTest {


    @Mock
    List<PreviewPublicacion> ListPreviewPublicacionMock;
    RepoPublicacion RepoPublicacionMock;
    PublicacionConverter PubliConverterMock;
    List<Publicacion> ListPublicacionMock;

    /**
     *  TEST DISCOVER BY AMIGO
     */
    @Test
    void discoverByAmigoTestNull() {

        Integer useridMock = Mockito.mock(Integer.class);

        when(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(useridMock);
        when(RepoPublicacionMock.fromFriends(useridMock)).thenReturn(ListPublicacionMock);
        when(ListPublicacionMock == null).thenReturn(true);

        assertNull(ListPreviewPublicacionMock);

    }

    @Test
    void discoverByAmigoTestOK() {

        Integer useridMock = Mockito.mock(Integer.class);

        when(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName())).thenReturn(useridMock);
        when(RepoPublicacionMock.fromFriends(useridMock)).thenReturn(ListPublicacionMock);
        when(ListPublicacionMock == null).thenReturn(false);

        assertNotNull(ListPreviewPublicacionMock);

    }



    /**
     *  TEST DISCOVER BEST RATED
     */

    @Test
    void discoverBestRatedTestNull() {
        /*
        String periodMock = Mockito.mock(String.class);
        String countryMock = Mockito.mock(String.class);
        String cityMock = Mockito.mock(String.class);
        Integer getDay = Mockito.mock(Integer.class);


        when(RepoPublicacionMock.bestRated(getDayAmount(periodMock), countryMock, cityMock)).thenReturn(ListPublicacionMock);
        when(ListPublicacionMock == null).thenReturn(true);

        assertNull(ListPreviewPublicacionMock);
        */
    }

    @Test
    void discoverBestRatedTestOK() {

        /*when().thenReturn();
        when().thenReturn();

        assertNull();
        */
    }

    /**
     *  TEST DISCOVER MOST RATED
     */
    @Test
    void discoverMostRated() {
    }

    /**
     *  TEST FIND FOLLOWED BY FRIENDS
     */
    @Test
    void findFollowedByFriends() {
    }

    /**
     *  TEST USER WHO FOLLOW X ALSO FOLLOW Y
     */

    @Test
    void userWhoFollowXAlsoFollowY() {
    }

    /**
     *  TEST FIND COLLABS
     */
    @Test
    void findCollabs() {
    }
}