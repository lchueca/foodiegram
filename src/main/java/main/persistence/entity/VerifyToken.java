package main.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
@Entity
public class VerifyToken {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer iduser;
    private Integer token;
    private Date expiredate;

    public VerifyToken(Usuario user, Integer securitytoken){
        this.iduser=user.getId();
        this.token=securitytoken;
        this.expiredate=calculateExpiryDate(20);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    public Integer getToken(){

        return this.token;
    }
    public Date getExpiredate(){

        return this.expiredate;
    }
    public Integer getIduser(){

        return this.iduser;
    }

    public Integer getId() {
        return id;
    }
}
