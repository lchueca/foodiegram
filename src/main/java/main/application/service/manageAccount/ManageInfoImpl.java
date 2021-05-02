package main.application.service.manageAccount;

import main.domain.converter.UsuarioConverter;
import main.domain.resource.UsuarioResource;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;

public class ManageInfoImpl implements ManageInfo{

    private final UsuarioConverter userConverter = new UsuarioConverter();

    @Autowired
    RepoUsuario repoUser;



    @Override
    public UsuarioResource changeName(Integer idUser, String newName) {

        Usuario user = repoUser.findById(idUser);

        if(user == null)
            return null;
        else{
            Usuario nameUnique = repoUser.findByName(newName);

            if(nameUnique == null)//si no hay usuarios con newName, podrá cambiar el nombre
                user.setName(newName);

            repoUser.save(user);
            return userConverter.convert(user);
        }
    }

    @Override
    public UsuarioResource changePasswd(Integer idUser, String newPasswd) {
        Usuario user = repoUser.findById(idUser);

        if(user == null)
            return null;
        else{
            user.setPasswd(newPasswd);
            repoUser.save(user);
            return userConverter.convert(user);
        }
    }

    @Override
    public UsuarioResource changeEmail(Integer idUser, String newMail) {
        Usuario user = repoUser.findById(idUser);

        if(user == null)
            return null;
        else{
            user.setEmail(newMail);
            repoUser.save(user);
            return userConverter.convert(user);
        }
    }

    @Override
    public UsuarioResource changeProfilePicture(Integer idUser, String newProfilePic) {
        Usuario user = repoUser.findById(idUser);

        if(user == null)
            return null;
        else{
            user.setImage(newProfilePic);
            repoUser.save(user);
            return userConverter.convert(user);
        }
    }
}
