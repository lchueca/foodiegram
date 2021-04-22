package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mensaje {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;
    private Integer IdUser1;
    private Integer IdUser2;
    private String Text;

    public Mensaje (Integer idUser1, Integer idUser2, String text) {
        IdUser1 = idUser1;
        IdUser2 = idUser2;
        Text = text;
    }

    protected Mensaje() {}

    public Integer getId() {
        return Id;
    }

    public Integer getIdUser1() {
        return IdUser1;
    }

    public Integer getIdUser2() {
        return IdUser2;
    }

    public String getText() {
        return Text;
    }
}
