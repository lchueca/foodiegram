package main.persistence.entity;

import javax.persistence.*;

@Entity
public class Publicacion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer id_user;
    private String text;
    private String image;
    private String  localization;

    public Publicacion(String text, Integer idUser, String image, String localization) {
        this.text = text;
        this.image = image;
        this.id_user = idUser;
        this.localization = localization;
    }

    public Publicacion(String image) {
        this.text = null;
        this.image = image;
        this.id_user = null;
        this.localization = null;
    }

    public Publicacion(){}

    public Integer getId() {
        return id;
    }

    public Integer getIduser() {
        return id_user;
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

    public void setIduser(Integer iduser) {
        this.id_user = iduser;
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
}
