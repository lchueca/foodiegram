package main.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer idcolab;
    private String text;
    private String image;
    private java.sql.Date endtime;

    public Evento(Integer idcolab, String text, java.sql.Date endtime) {
        this.idcolab = idcolab;
        this.text = text;
        this.endtime = endtime;
        this.image = null;
    }
}
