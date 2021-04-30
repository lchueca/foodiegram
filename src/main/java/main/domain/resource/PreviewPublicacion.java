package main.domain.resource;

import java.util.Objects;

// Se usa para sacar solo de la DB el ID y la imagen de la publicacion.
public class PreviewPublicacion {

    private final Integer id;
    private final String image;

    public PreviewPublicacion(Integer id, String image) {
        this.id = id;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreviewPublicacion that = (PreviewPublicacion) o;
        return id.equals(that.id) && image.equals(that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image);
    }
}