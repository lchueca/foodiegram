package main.persistence.entity;

import main.persistence.IDs.IDMensajes;

import javax.persistence.*;

@Entity
@IdClass(IDMensajes.class)
public class Mensaje {
    @Id

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Id
    private Integer idUser1;
    @Id
    private Integer idUser2;
    private String text;

    public Mensaje (Integer idUser1, Integer idUser2, String text) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.text = text;
    }

    protected Mensaje() {}

    public Integer getId() {
        return id;
    }

    public Integer getIdUser1() {
        return idUser1;
    }

    public Integer getIdUser2() {
        return idUser2;
    }

    public String getText() {
        return text;
    }
}
