package main.persistence.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Jwtoken {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private Date expiredate;

    public Jwtoken(String username, Date expiredate) {
        this.username = username;
        this.expiredate = expiredate;
    }

    public Jwtoken() {
    }

}
