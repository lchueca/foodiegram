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
    private Integer id_publi;
    private Integer id_user;
    private String text;

    public Comentario(Integer idPubli, Integer idUser, String text) {
        this.id_publi = idPubli;
        id_publi = idUser;
        this.text = text;
    }

    protected Comentario() {}

    public Integer getId() {
        return id;
    }

    public Integer getIdPubli() {
        return id_publi;
    }

    public Integer getIdUser() {
        return id_user;
    }

    public String getText() {
        return text;
    }
}
