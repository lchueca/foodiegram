package main.domain.converter;

import main.domain.resource.UsuarioResource;
import main.persistence.entity.Usuario;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter implements Converter<Usuario, UsuarioResource> {

    @Override
    public UsuarioResource convert(Usuario source ){

        if (source == null)
            return null;

        UsuarioResource response = new UsuarioResource();
        response.setId(source.getId());
        response.setName(source.getName());
        response.setEmail(source.getEmail());
        response.setImage(source.getImage());
        return response;
    }
}
