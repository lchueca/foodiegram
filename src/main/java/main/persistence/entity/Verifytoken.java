package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
@Entity
public class Verifytoken {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private Integer token;
    private Date expiredate;

    public Verifytoken(String email, Integer securitytoken){
        this.email=email;
        this.token=securitytoken;
        this.expiredate=calculateExpiryDate();
    }

    protected Verifytoken(){}

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime().getTime());
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, 20);
        return new Date(cal.getTime().getTime());
    }

    public Integer getToken(){

        return this.token;
    }
    public Date getExpiredate(){

        return this.expiredate;
    }
    public String getEmail(){

        return this.email;
    }

    public Integer getId() {
        return id;
    }
}
