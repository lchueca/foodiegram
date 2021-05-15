package main.domain.converter;

import main.domain.resource.PreviewColabJOINUser;
import main.persistence.entity.ColabJOINUser;
import org.springframework.core.convert.converter.Converter;

public class PreviewColabJOINUserConverter implements Converter<ColabJOINUser, PreviewColabJOINUser> {

    @Override
    public PreviewColabJOINUser convert(ColabJOINUser source) {
        return source != null ? new PreviewColabJOINUser(source.getName(), source.getOrigin(), source.getType(), source.getLocalization(), source.getImage()) : null;
    }
}
