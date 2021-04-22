package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;
    private Integer IdPubli;
    private Integer IdUser;
    private String Text;

    public Comentario(Integer idPubli, Integer idUser, String text) {
        IdPubli = idPubli;
        IdUser = idUser;
        Text = text;
    }

    protected Comentario() {}

    public Integer getId() {
        return Id;
    }

    public Integer getIdPubli() {
        return IdPubli;
    }

    public Integer getIdUser() {
        return IdUser;
    }

    public String getText() {
        return Text;
    }
}
