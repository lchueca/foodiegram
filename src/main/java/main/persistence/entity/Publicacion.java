package main.persistence.entity;

import main.persistence.repository.RepoUsuario;
import main.security.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.NoPermissionException;
import javax.persistence.*;

@Entity
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

    public Publicacion(String image) {
        this.text = null;
        this.image = image;
        this.iduser = null;
        this.localization = null;
        this.media=null;
        this.numerototalval=null;
    }

    public Publicacion(){}

    public Integer getId() {
        return id;
    }

    public Integer getIduser() {
        return iduser;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public String getLocalization() {
        return localization;
    }

    public Float getMedia() { return media; }

    public Integer getNumerototalval() { return numerototalval; }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    @PreRemove
    @PreUpdate
    private void preventUnauthorizedRemove() throws NoPermissionException {

        Integer deleterId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!deleterId.equals(iduser))
            throw new NoPermissionException("You're not allowed to do that");
    }

}
