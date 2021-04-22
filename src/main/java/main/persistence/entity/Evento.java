package main.persistence.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

public class Evento {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;
    private String Text;
    private byte[] Image;
    private java.sql.Date date;
    private Integer IdUser1;
    private Integer IdUser2;
    private Integer IdUser3;
    private Integer IdUser4;
    private Integer IdUser5;
    private Integer IdUser6;
    private Integer IdUser7;
    private Integer IdUser8;
    private Integer IdUser9;
    private Integer IdUser10;

    public Evento(String text, byte[] image, Date date, Integer idUser1, Integer idUser2, Integer idUser3, Integer idUser4, Integer idUser5, Integer idUser6, Integer idUser7, Integer idUser8, Integer idUser9, Integer idUser10) {
        Text = text;
        Image = image;
        this.date = date;
        IdUser1 = idUser1;
        IdUser2 = idUser2;
        IdUser3 = idUser3;
        IdUser4 = idUser4;
        IdUser5 = idUser5;
        IdUser6 = idUser6;
        IdUser7 = idUser7;
        IdUser8 = idUser8;
        IdUser9 = idUser9;
        IdUser10 = idUser10;
    }


    protected  Evento() {}

    public Integer getId() {
        return Id;
    }

    public String getText() {
        return Text;
    }

    public byte[] getImage() {
        return Image;
    }

    public Date getDate() {
        return date;
    }

    public Integer getIdUser1() {
        return IdUser1;
    }

    public Integer getIdUser2() {
        return IdUser2;
    }

    public Integer getIdUser3() {
        return IdUser3;
    }

    public Integer getIdUser4() {
        return IdUser4;
    }

    public Integer getIdUser5() {
        return IdUser5;
    }

    public Integer getIdUser6() {
        return IdUser6;
    }

    public Integer getIdUser7() {
        return IdUser7;
    }

    public Integer getIdUser8() {
        return IdUser8;
    }

    public Integer getIdUser9() {
        return IdUser9;
    }

    public Integer getIdUser10() {
        return IdUser10;
    }
}
