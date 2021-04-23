package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MeetUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer IdUser;

    public MeetUp(Integer id, Integer idUser) {
        Id = id;
        IdUser = idUser;
    }

    protected MeetUp() {}

    public Integer getId() {
        return Id;
    }

    public Integer getIdUser() {
        return IdUser;
    }
}