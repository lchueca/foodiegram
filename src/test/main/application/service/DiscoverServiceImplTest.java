package main.application.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import main.domain.converter.PreviewColabJOINUserConverter;
import main.domain.converter.PublicacionConverter;
import main.domain.converter.UsuarioConverter;
import main.domain.resource.PreviewColabJOINUser;
import main.domain.resource.PreviewPublicacion;
import main.domain.resource.PreviewUsuario;
import main.persistence.entity.ColabJOINUser;
import main.persistence.entity.Publicacion;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoColabJOINUser;
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
import java.util.stream.Collectors;


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
     *  TEST DISCOVER BEST RATED ///// GET DAY AMOUNT
     */

    @Test
    void discoverBestRatedTestNull() {

        String periodMock = Mockito.mock(String.class);
        String countryMock = Mockito.mock(String.class);
        String cityMock = Mockito.mock(String.class);
        Integer getDay = Mockito.mock(Integer.class);


        when(RepoPublicacionMock.bestRated(DiscoverServiceImpl.getDayAmount(periodMock), countryMock, cityMock)).thenReturn(ListPublicacionMock);
        when(ListPublicacionMock == null).thenReturn(true);

        assertNull(ListPreviewPublicacionMock);

    }

    @Test
    void discoverBestRatedTestOK() {

        String periodMock = Mockito.mock(String.class);
        String countryMock = Mockito.mock(String.class);
        String cityMock = Mockito.mock(String.class);
        Integer getDay = Mockito.mock(Integer.class);


        when(RepoPublicacionMock.bestRated(DiscoverServiceImpl.getDayAmount(periodMock), countryMock, cityMock)).thenReturn(ListPublicacionMock);
        when(ListPublicacionMock == null).thenReturn(false);

        assertNotNull(ListPreviewPublicacionMock);

    }

    /**
     *  TEST DISCOVER MOST RATED  ///// GET DAY AMOUNT
     */
    @Test
    void discoverMostRatedTestNull() {

        String periodMock = Mockito.mock(String.class);

        when(RepoPublicacionMock.mostRated(DiscoverServiceImpl.getDayAmount(periodMock))).thenReturn(ListPublicacionMock);
        when(ListPublicacionMock == null).thenReturn(true);

        assertNull(ListPublicacionMock);

    }

    @Test
    void discoverMostRatedTestOK() {

        String periodMock = Mockito.mock(String.class);

        when(RepoPublicacionMock.mostRated(DiscoverServiceImpl.getDayAmount(periodMock))).thenReturn(ListPublicacionMock);
        when(ListPublicacionMock == null).thenReturn(false);

        assertNotNull(ListPreviewPublicacionMock);

    }



    /**
     *  TEST FIND FOLLOWED BY FRIENDS
     */

    @Mock

    RepoUsuario repoUsuarioMock;
    UsuarioConverter usuarioConverterMock;
    Usuario usuarioMock;
    List<PreviewUsuario> ListPreviewUserMock;
    List<Usuario> ListUsuarioMock;

    @Test
    void findFollowedByFriendsTestOk() {

        Integer userid = Mockito.mock(Integer.class);

       when(repoUsuarioMock.findFollowedByFriends(userid)).thenReturn(ListUsuarioMock);

       assertNotNull(ListUsuarioMock);

    }



    /**
     *  TEST USER WHO FOLLOW X ALSO FOLLOW Y
     */

    @Test
    void userWhoFollowXAlsoFollowYTestNull() {

        String userMock = Mockito.mock(String.class);

        when(repoUsuarioMock.findByName(userMock)).thenReturn(usuarioMock);
        when(usuarioMock == null).thenReturn(true);

        assertNull(ListPreviewPublicacionMock);

    }


    @Test
    void userWhoFollowXAlsoFollowYTestOK() {

        String userMock = Mockito.mock(String.class);

        when(repoUsuarioMock.findByName(userMock)).thenReturn(usuarioMock);
        when(usuarioMock == null).thenReturn(true);
        when(repoUsuarioMock.usersFollowedByUsersWhoFollow(usuarioMock.getId())).thenReturn(ListUsuarioMock);

        assertNotNull(ListPreviewPublicacionMock);

    }

    /**
     *  TEST FIND COLLABS
     */

    @Mock
    RepoColabJOINUser repoColabJOINUserMock;
    List<ColabJOINUser> ListColabJOINUserMock;
    List<PreviewColabJOINUser>  ListPreviewColabJOINUserMock;

    @Test
    void findCollabs() {

        String countryMock = Mockito.mock(String.class);
        String cityMock = Mockito.mock(String.class);

        when(repoColabJOINUserMock.descubrirCollab(countryMock,cityMock)).thenReturn(ListColabJOINUserMock);

        assertNotNull(ListPreviewColabJOINUserMock);

    }
}