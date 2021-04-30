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
    private Integer id;
    private String text;
    private byte[] image;
    private java.sql.Date date;


    public Evento(String text, byte[] image, Date date) {
        this.text = text;
        this.image = image;
        this.date = date;

    }


    protected  Evento() {}

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public byte[] getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }


}
