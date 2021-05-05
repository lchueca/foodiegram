package main.application.service;

import main.domain.converter.MensajeConverter;
import main.domain.resource.MensajeResource;
import main.persistence.entity.Mensaje;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoMensaje;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensajeServiceImpl implements MensajeService{


    private final MensajeConverter converterMens = new MensajeConverter();

    @Autowired
    RepoMensaje repoMens;
    @Autowired
    RepoUsuario repoUser;


    @Override
    public MensajeResource getMensaje(Integer mensID) {
        return converterMens.convert(repoMens.findOne(mensID));
    }


    @Override
    public MensajeResource deleteMensaje(Integer mensID) {
        Mensaje mens = repoMens.findOne(mensID);

        if (mens != null)
            repoMens.delete(mens);

        return converterMens.convert(mens);


    }

    @Override
    public MensajeResource setMensaje(String userName1, String userName2, String mensaje) throws IllegalArgumentException {

        Usuario user1 = repoUser.findByName(userName1);
        Usuario user2 = repoUser.findByName(userName2);

        if (user1 == null || user2 == null)
            throw new IllegalArgumentException("Both users should exist.");


        Mensaje mens = new Mensaje(user1.getId(), user2.getId(), mensaje);
        repoMens.save(mens);
        return converterMens.convert(mens);

    }

}
