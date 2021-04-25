package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Amigo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id_user1;
    private Integer id_user2;

    public Amigo(Integer idUser1, Integer idUser2) {
        id_user1 = idUser1;
        id_user2 = idUser2;
    }

    protected Amigo() {}

    public Integer getIdUser1() {
        return id_user1;
    }

    public Integer getIdUser2() {
        return id_user2;
    }
}
