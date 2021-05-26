package main.application.service;

import main.domain.converter.MensajeConverter;
import main.domain.resource.MensajeResource;
import main.persistence.entity.Mensaje;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoMensaje;
import main.persistence.repository.RepoUsuario;
import main.rest.forms.MessageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
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
    public MensajeResource deleteMensaje(Integer mensID) throws NoPermissionException {
        Mensaje mens = repoMens.findOne(mensID);

        if (mens != null)
            repoMens.delete(mens);

        return converterMens.convert(mens);

    }

    @Override
    public MensajeResource setMensaje(Integer userID, MessageForm mensaje) throws IllegalArgumentException {

        Usuario user2 = repoUser.findByName(mensaje.getReceiver());

        if (user2 == null)
            throw new IllegalArgumentException("That user does not exist.");


        Mensaje mens = new Mensaje(userID, user2.getId(), mensaje.getText());
        repoMens.save(mens);
        return converterMens.convert(mens);

    }

    @Override
    public List<MensajeResource> getMensajes(Integer userID) {


        Usuario user = repoUser.findOne(userID);

        List<Mensaje> mensajes = repoMens.findByIduser1OrIduser2(user.getId(), user.getId());
        return mensajes.stream().map(converterMens::convert).collect(Collectors.toList());

    }

}
