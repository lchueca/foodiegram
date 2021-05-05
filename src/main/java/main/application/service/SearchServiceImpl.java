package main.application.service;

import main.domain.converter.PreviewColabJOINUserConverter;
import main.domain.converter.PreviewUserConverter;
import main.domain.resource.PreviewColabJOINUser;
import main.domain.resource.PreviewUsuario;
import main.persistence.entity.ColabJOINUser;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoColabJOINUser;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final PreviewUserConverter converterPreviewUser = new PreviewUserConverter();
    private final PreviewColabJOINUserConverter converterPreviewColabJUser = new PreviewColabJOINUserConverter();

    @Autowired
    private RepoUsuario repoUser;

    @Autowired
    private RepoColabJOINUser repoColabJUser;

    // BUSQUEDA DE USUARIOS
    //
    // devuelve una lista de usuarios cuyo nombre contenga username
    public List<PreviewUsuario> getUserList(String username) {

        List<Usuario> userList = repoUser.findBynameContainingIgnoreCase(username);
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

}
