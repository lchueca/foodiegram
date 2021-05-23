package main.application.service;


import main.domain.converter.ColaboradorConverter;
import main.domain.resource.ColaboradorResource;
import main.persistence.entity.Colaborador;
import main.persistence.entity.RoleEnum;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoColaborador;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    private ColaboradorConverter converterCol = new ColaboradorConverter();

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoColaborador repoColaborador;

    @Override
    public ColaboradorResource upgradeUser(Integer userid,String origin,String type,String localization) {

        Usuario user = repoUsuario.findOne(userid);

        if(userid==null)
            return null;

        Colaborador colaborador=new Colaborador(user.getId(),origin,type,localization);
        user.setRole(RoleEnum.ROLE_COL);
        repoUsuario.save(user);
        repoColaborador.save(colaborador);

        return converterCol.convert(colaborador);
    }
}
