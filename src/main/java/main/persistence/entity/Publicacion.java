package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Publicacion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;
    private Integer IdUser;
    private String Text;
    private byte[] Image;
    private String  Localization;

    public Publicacion(Integer idUser, String text, byte[] image, String localization) {
        IdUser = idUser;
        Text = text;
        Image = image;
        Localization = localization;
    }

    protected  Publicacion(){}

    public Integer getId() {
        return Id;
    }

    public Integer getIdUser() {
        return IdUser;
    }

    public String getText() {
        return Text;
    }

    public byte[] getImage() {
        return Image;
    }

    public String getLocalization() {
        return Localization;
    }
}
