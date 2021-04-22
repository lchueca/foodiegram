package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;
    private String Text;
    private byte[] Image;
    private java.sql.Date date;


    public Evento(String text, byte[] image, Date date) {
        Text = text;
        Image = image;
        this.date = date;

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


}
