package main.domain.converter;

import main.domain.resource.Usuario_baneadoResource;
import main.persistence.entity.Usuario_baneado;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Usuario_baneadoConverter implements Converter<Usuario_baneado, Usuario_baneadoResource> {
        @Override
        public Usuario_baneadoResource convert (Usuario_baneado source){

            if(source==null){
                return null;
            }
            Usuario_baneadoResource response=new Usuario_baneadoResource();
            response.setDate(source.getDate());
            response.setId(source.getId());
            return response;

        }


}
