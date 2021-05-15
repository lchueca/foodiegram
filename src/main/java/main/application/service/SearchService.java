package main.application.service;

import main.domain.resource.PreviewColabJOINUser;
import main.domain.resource.PreviewPubliJOINUser;
import main.domain.resource.PreviewUsuario;

import java.util.List;

public interface SearchService {

    List<PreviewUsuario> getUserList(String username);
    List<PreviewColabJOINUser> getColabListByName(String colabname);
    List<PreviewColabJOINUser> getColabListByOrigin(String origin);
    List<PreviewColabJOINUser> getColabListByType(String type);
    List<PreviewPubliJOINUser> getPubliListByTag(String tag);
}
