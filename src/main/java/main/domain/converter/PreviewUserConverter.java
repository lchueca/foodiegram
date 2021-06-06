package main.domain.converter;

import main.domain.resource.PreviewUsuario;
import main.persistence.entity.Usuario;
import org.springframework.core.convert.converter.Converter;

public class PreviewUserConverter implements Converter<Usuario, PreviewUsuario> {

    @Override
    public PreviewUsuario convert(Usuario source) {
        return source != null ? new PreviewUsuario(source.getName(), source.getImage()) : null;
    }
}
