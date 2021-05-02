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
    public List<MensajeResource> getMensajes(Integer user1ID) {

        Usuario user = repoUser.findOne(user1ID);

        if (user == null)
            return null;

        else {
            List<Mensaje> mensajes = repoMens.findByiduser1(user1ID);
            return mensajes.stream().map(converterMens::convert).collect(Collectors.toList());
        }
    }

    @Override
    public MensajeResource setMensaje(Integer user1ID, Integer user2ID, String mensaje) throws DataIntegrityViolationException {

        Mensaje mens = new Mensaje(user1ID,user2ID,mensaje);
        repoMens.save(mens);
        return converterMens.convert(mens);

    }

}
