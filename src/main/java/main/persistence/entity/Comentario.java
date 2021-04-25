package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer idpubli;
    private Integer iduser;
    private String text;

    public Comentario(Integer idPubli, Integer idUser, String text) {
        this.idpubli = idPubli;
        iduser = idUser;
        this.text = text;
    }

    protected Comentario() {}

    public Integer getId() {
        return id;
    }

    public Integer getIdPubli() {
        return idpubli;
    }

    public Integer getIdUser() {
        return iduser;
    }

    public String getText() {
        return text;
    }
}
