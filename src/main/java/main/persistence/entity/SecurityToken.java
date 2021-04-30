package main.persistence.entity;



import main.persistence.entity.Usuario;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class SecurityToken {

    private int id;
    private String token;
    private Date expireDate;
    public SecurityToken(Usuario user, String securitytoken){
        this.id=user.getId();
        this.token=securitytoken;
        this.expireDate=calculateExpiryDate(20);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    public String getToken(){

        return this.token;
    }

}
