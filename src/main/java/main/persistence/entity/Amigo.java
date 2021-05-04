package main.persistence.entity;

import main.persistence.IDs.IDamigo;

import javax.persistence.*;

@Entity
@IdClass(IDamigo.class)
public class Amigo {

    @Id
    private Integer iduser1;
    @Id
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
