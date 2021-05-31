package main.domain.converter;

import main.domain.resource.PreviewPublicacion;
import main.persistence.entity.Publicacion;
import org.springframework.core.convert.converter.Converter;

import java.text.DecimalFormat;


public class PreviewPublicacionConverter implements Converter<Publicacion, PreviewPublicacion> {

    @Override
    public PreviewPublicacion convert(Publicacion source) {

        DecimalFormat df = new DecimalFormat("#.##");
        return source != null ? new PreviewPublicacion(source.getId(), source.getImage(), source.getFecha().toString(), df.format(source.getMedia()), source.getText()) : null;
    }
}
