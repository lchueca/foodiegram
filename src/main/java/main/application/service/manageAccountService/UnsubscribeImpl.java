package main.application.service.manageAccountService;

import main.domain.converter.UsuarioConverter;
import main.domain.resource.UsuarioResource;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoAmigo;
import main.persistence.repository.RepoPublicacion;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnsubscribeImpl implements  Unsubscribe{

    private final UsuarioConverter userConverter = new UsuarioConverter();

    @Autowired
    RepoUsuario repoUser;

    @Override
    public UsuarioResource unsubscribe(Integer userId) {
        Usuario user = repoUser.findById(userId);

        if(user != null){

            repoUser.delete(user);
        }

        return userConverter.convert(user);
    }
}
