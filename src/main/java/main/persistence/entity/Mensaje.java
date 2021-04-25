package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mensaje {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer iduser1;
    private Integer iduser2;
    private String text;

    public Mensaje (Integer idUser1, Integer idUser2, String text) {
        iduser1 = idUser1;
        iduser2 = idUser2;
        this.text = text;
    }

    protected Mensaje() {}

    public Integer getId() {
        return id;
    }

    public Integer getIdUser1() {
        return iduser1;
    }

    public Integer getIdUser2() {
        return iduser2;
    }

    public String getText() {
        return text;
    }
}
