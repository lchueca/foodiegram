package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Amigo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer iduser1;
    private Integer iduser2;

    public Amigo(Integer idUser1, Integer idUser2) {
        iduser1 = idUser1;
        iduser2 = idUser2;
    }

    protected Amigo() {}

    public Integer getIdUser1() {
        return iduser1;
    }

    public Integer getIdUser2() {
        return iduser2;
    }
}
