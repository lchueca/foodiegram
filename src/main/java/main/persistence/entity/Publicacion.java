package main.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.NoPermissionException;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Publicacion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer iduser;
    private String text;
    private String image;
    private String  localization;
    private  Float media;
    private Integer numerototalval;

    public Publicacion(String text, Integer idUser, String image, String localization) {
        this.text = text;
        this.image = image;
        this.iduser = idUser;
        this.localization = localization;
        this.media=0f;
        this.numerototalval=0;
    }

    public Publicacion(String text, Integer idUser, String localization) {
        this.text = text;
        this.image = null;
        this.iduser = idUser;
        this.localization = localization;
        this.media=0f;
        this.numerototalval=0;
    }

    @PreRemove
    @PreUpdate
    private void preventUnauthorizedRemove() throws NoPermissionException {

        Integer deleterId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!deleterId.equals(iduser))
            throw new NoPermissionException("You're not allowed to do that");
    }

}
