package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MeetUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer iduser;

    public MeetUp(Integer id, Integer idUser) {
        this.id = id;
        this.iduser = idUser;
    }

    protected MeetUp() {}

    public Integer getId() {
        return id;
    }

    public Integer getIdUser() {
        return iduser;
    }
}