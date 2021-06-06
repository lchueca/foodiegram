package main.application.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


import main.domain.converter.PreviewColabJOINUserConverter;
import main.domain.converter.PreviewPubliJOINUserConverter;
import main.domain.converter.PreviewUserConverter;
import main.domain.resource.PreviewColabJOINUser;
import main.domain.resource.PreviewPubliJOINUser;
import main.domain.resource.PreviewUsuario;
import main.persistence.entity.ColabJOINUser;
import main.persistence.entity.PubliJOINUser;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoColabJOINUser;
import main.persistence.repository.RepoPubliJOINUser;
import main.persistence.repository.RepoUsuario;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.stream.Collectors;


@ExtendWith(MockitoExtension.class)
class SearchServiceImplTest {

    /**
     * TEST GET USER LIST
     */

    @Mock
    List<PreviewUsuario> PrevUsuarioMock;
    List<Usuario> userListMock;
    RepoUsuario repoUserMock;
    PreviewUserConverter converterPrevUsuarioMock;

    @Test
    void getUserList() { //X

        String userName = "Ahsoka";
        when(repoUserMock.findBynameContainingIgnoreCase(userName)).thenReturn(userListMock);
        when(userListMock.stream().map(converterPrevUsuarioMock::convert).collect(Collectors.toList())).thenReturn(PrevUsuarioMock);
        assertNotNull(PrevUsuarioMock);

    }



    /**
     * TEST GET COLAB LIST BY NAME
     */

    @Mock
    List<PreviewColabJOINUser> ColabListByNameMock;
    List<ColabJOINUser> colabJuserMock;
    RepoColabJOINUser RepoColabJOINUserMock;
    PreviewColabJOINUserConverter PreviewColabJOINUserConverterMock;

    @Test
    void getColabListByName() { //X

        String colabMock = "Mos Eisley";
        when(RepoColabJOINUserMock.findByUsername(colabMock)).thenReturn(colabJuserMock);
        when(colabJuserMock.stream().map(PreviewColabJOINUserConverterMock::convert).collect(Collectors.toList())).thenReturn(ColabListByNameMock);
        assertNotNull(ColabListByNameMock);

    }



    /**
     * TEST GET COLAB LIST BY ORIGIN
     */

    @Mock
    List<PreviewColabJOINUser> ColabListByOriginMock;

    @Test
    void getColabListByOrigin() { //X

        String origin = "Tatooine";
        when(RepoColabJOINUserMock.findByOrigin(origin)).thenReturn(colabJuserMock);
        when(colabJuserMock.stream().map(PreviewColabJOINUserConverterMock::convert).collect(Collectors.toList())).thenReturn(ColabListByOriginMock);
        assertNotNull(ColabListByOriginMock);

    }



    /**
     * TEST GET COLAB LIST BY TYPE
     */

    @Mock
    List<PreviewColabJOINUser> ColabListByTypeMock;

    @Test
    void getColabListByType() { //X

        String type = "Veggie";
        when(RepoColabJOINUserMock.findByType(type)).thenReturn(colabJuserMock);
        when(colabJuserMock.stream().map(PreviewColabJOINUserConverterMock::convert).collect(Collectors.toList())).thenReturn(ColabListByTypeMock);
        assertNotNull(ColabListByTypeMock);

    }



    /**
     * TEST GET PUBLI LIST BY TAG
     */

    @Mock
    List<PreviewPubliJOINUser> publiListByTagMock;
    List<PubliJOINUser> publiJuserMock;
    RepoPubliJOINUser repoPubliJUserMock;
    PreviewPubliJOINUserConverter converterPreviewPubliJUserMock;

    @Test
    void getPubliListByTag(){ //X

        String tag = "Cheap";
        when(repoPubliJUserMock.findByTag(tag)).thenReturn(publiJuserMock);
        when(publiJuserMock.stream().map(converterPreviewPubliJUserMock::convert).collect(Collectors.toList())).thenReturn(publiListByTagMock);
        assertNotNull(publiListByTagMock);

    }

    @Mock
    ResponseEntity<List<PreviewUsuario>> ResponseEntityMock;
    SearchService serviceMock;

    @Test
    void getUserListByPubliOk(){ //x
        when(serviceMock.getUserListByPubli()).thenReturn(PrevUsuarioMock);
        when(PrevUsuarioMock != null).thenReturn(true);
        when(ResponseEntity.ok(PrevUsuarioMock)).thenReturn(ResponseEntityMock);
        assertNotNull(ResponseEntityMock);
    }

    @Test
    void getUserListByPubliNull(){ //x
        when(serviceMock.getUserListByPubli()).thenReturn(PrevUsuarioMock);
        when(PrevUsuarioMock != null).thenReturn(false);
        when(ResponseEntity.notFound().build()).thenReturn(null);
        assertNull(ResponseEntityMock);
    }

    @Test
    void getUserListByValOk(){ //ok
        when(serviceMock.getUserListByVal()).thenReturn(PrevUsuarioMock);
        when(PrevUsuarioMock != null).thenReturn(true);
        when(ResponseEntity.ok(PrevUsuarioMock)).thenReturn(ResponseEntityMock);
        assertNotNull(ResponseEntityMock);
    }

    @Test
    void getUserListByValNull(){ //ok
        when(serviceMock.getUserListByVal()).thenReturn(PrevUsuarioMock);
        when(PrevUsuarioMock != null).thenReturn(false);
        when(ResponseEntity.notFound().build()).thenReturn(null);
        assertNull(ResponseEntityMock);
    }

}