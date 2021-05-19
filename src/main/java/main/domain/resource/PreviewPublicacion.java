package main.domain.resource;

import lombok.Data;

// Se usa para sacar solo de la DB el ID y la imagen de la publicacion.
@Data
public class PreviewPublicacion {

    private final Integer id;
    private final String image;

    public PreviewPublicacion(Integer id, String image) {
        this.id = id;
        this.image = image;
    }

}