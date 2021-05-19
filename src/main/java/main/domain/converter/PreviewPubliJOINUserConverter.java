package main.domain.converter;

import main.domain.resource.PreviewPubliJOINUser;
import main.persistence.entity.PubliJOINUser;
import org.springframework.core.convert.converter.Converter;

public class PreviewPubliJOINUserConverter implements Converter<PubliJOINUser, PreviewPubliJOINUser> {

    @Override
    public PreviewPubliJOINUser convert(PubliJOINUser source) {
        return source != null ? new PreviewPubliJOINUser(source.getName(), source.getUserimage(), source.getText(), source.getImage(),
                source.getLocalization(), source.getMedia(), source.getNumerototalval()) : null;
    }
}
