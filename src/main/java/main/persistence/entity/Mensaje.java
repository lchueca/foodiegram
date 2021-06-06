package main.persistence.entity;

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
public class Mensaje {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer iduser1;
    private Integer iduser2;
    private String text;

    public Mensaje (Integer idUser1, Integer idUser2, String text) {
        this.iduser1 = idUser1;
        this.iduser2 = idUser2;
        this.text = text;
    }

    @PreRemove
    private void preventUnauthorizedRemove() throws ForbiddenException {

        Integer deleterId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (!deleterId.equals(iduser1) && !authorities.contains(RoleEnum.ROLE_MOD) && !authorities.contains(RoleEnum.ROLE_ADMIN))
            throw new ForbiddenException("You're not allowed to do that");
    }

}
