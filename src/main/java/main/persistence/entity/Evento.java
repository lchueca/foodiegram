package main.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.security.ForbiddenException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.NoPermissionException;
import javax.persistence.*;
import java.util.Collection;

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


    @PreRemove
    @PreUpdate
    private void preventUnauthorizedRemove() throws ForbiddenException {

        Integer deleterId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (!deleterId.equals(idcolab) && !authorities.contains(RoleEnum.ROLE_COL))
            throw new ForbiddenException("You're not allowed to do that");
    }
}
