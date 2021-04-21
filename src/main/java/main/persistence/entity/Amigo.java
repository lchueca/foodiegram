package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Amigo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer IdUser1;
    private Integer IdUser2;

    public Amigo(Integer idUser1, Integer idUser2) {
        IdUser1 = idUser1;
        IdUser2 = idUser2;
    }

    protected Amigo() {}

    public Integer getIdUser1() {
        return IdUser1;
    }

    public Integer getIdUser2() {
        return IdUser2;
    }
}
