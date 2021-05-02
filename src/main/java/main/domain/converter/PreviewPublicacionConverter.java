package main.domain.converter;

import main.domain.resource.PreviewPublicacion;
import main.persistence.entity.Publicacion;
import org.springframework.core.convert.converter.Converter;


public class PreviewPublicacionConverter implements Converter<Publicacion, PreviewPublicacion> {

    @Override
    public PreviewPublicacion convert(Publicacion source) {

       return source != null ? new PreviewPublicacion(source.getId(), source.getImage()) : null;
    }
}
