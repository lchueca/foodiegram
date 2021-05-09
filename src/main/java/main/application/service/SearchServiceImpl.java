package main.application.service;

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
import main.persistence.repository.RepoNumValPubli;
import main.persistence.repository.RepoPubliJOINUser;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final PreviewUserConverter converterPreviewUser = new PreviewUserConverter();
    private final PreviewColabJOINUserConverter converterPreviewColabJUser = new PreviewColabJOINUserConverter();
    private final PreviewPubliJOINUserConverter converterPreviewPubliJUser = new PreviewPubliJOINUserConverter();

    @Autowired
    private RepoUsuario repoUser;
    @Autowired
    private RepoColabJOINUser repoColabJUser;
    @Autowired
    private RepoPubliJOINUser repoPubliJUser;

    // BUSQUEDA DE USUARIOS
    //
    // devuelve una lista de usuarios cuyo nombre contenga username
    public List<PreviewUsuario> getUserList(String username) {

        List<Usuario> userList = repoUser.findBynameContainingIgnoreCase(username);
        return userList.stream().map(converterPreviewUser::convert).collect(Collectors.toList());
    }

    // devuelve una lista de usuarios por numero de publicaciones
    public List<PreviewUsuario> getUserListByPubli() {

        List<Usuario> userList = repoUser.findByPopuPubli();
        return userList.stream().map(converterPreviewUser::convert).collect(Collectors.toList());
    }

    // devuelve una lista de usuarios por numero de valoraciones recibidas
    public List<PreviewUsuario> getUserListByVal() {

        List<Usuario> userList = repoUser.findByPopuVal();
        return userList.stream().map(converterPreviewUser::convert).collect(Collectors.toList());
    }

    // BUSQUEDA DE LOCALES
    //
    // devuelve una lista de colaboradores cuyo nombre contenga colabname
    public List<PreviewColabJOINUser> getColabListByName(String colabname) {

        List<ColabJOINUser> colabJuser = repoColabJUser.findByUsername(colabname);
        return colabJuser.stream().map(converterPreviewColabJUser::convert).collect(Collectors.toList());
    }

    // devuelve una lista de colaboradores cuyo origin contenga origin
    public List<PreviewColabJOINUser> getColabListByOrigin(String origin) {

        List<ColabJOINUser> colabJuser = repoColabJUser.findByOrigin(origin);
        return colabJuser.stream().map(converterPreviewColabJUser::convert).collect(Collectors.toList());
    }

    // devuelve una lista de colaboradores cuyo type contenga type
    public List<PreviewColabJOINUser> getColabListByType(String type) {

        List<ColabJOINUser> colabJuser = repoColabJUser.findByType(type);
        return colabJuser.stream().map(converterPreviewColabJUser::convert).collect(Collectors.toList());
    }

    // BUSQUEDA DE PUBLICACIONES
    //
    // devuelve una lista de publicaciones cuyo texto contenga un hastag coincidente con tag
    public List<PreviewPubliJOINUser> getPubliListByTag(String tag) {

        List<PubliJOINUser> publiJuser = repoPubliJUser.findByTag(tag);
        return publiJuser.stream().map(converterPreviewPubliJUser::convert).collect(Collectors.toList());
    }
}
