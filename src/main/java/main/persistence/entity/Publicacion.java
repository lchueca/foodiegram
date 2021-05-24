package main.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.NoPermissionException;
import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
public class Publicacion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer iduser;
    private String text;
    private String image;
    private String pais;
    private String  ciudad;
    private  Float media;
    private Integer numerototalval;
    private Date fecha;

    public Publicacion(String text, Integer idUser, String image, String pais, String ciudad) {
        this.text = text;
        this.image = image;
        this.iduser = idUser;
        this.pais = pais;
        this.ciudad = ciudad;
        this.media=0f;
        this.numerototalval=0;
        this.fecha = new Date(Calendar.getInstance().getTime().getTime());
    }

    public Publicacion(String text, Integer idUser,String pais, String ciudad) {
        this.text = text;
        this.image = null;
        this.iduser = idUser;
        this.pais = pais;
        this.ciudad = ciudad;
        this.media=0f;
        this.numerototalval=0;
        this.fecha = new Date(Calendar.getInstance().getTime().getTime());
    }

    @PreRemove
    @PreUpdate
    private void preventUnauthorizedRemove() throws NoPermissionException {

        Integer deleterId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (!deleterId.equals(iduser) && !authorities.contains(RoleEnum.ROLE_MOD) && !authorities.contains(RoleEnum.ROLE_ADMIN))
            throw new NoPermissionException("You're not allowed to do that");
    }

}
