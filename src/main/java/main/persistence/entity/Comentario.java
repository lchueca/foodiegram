package main.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.NoPermissionException;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer idpubli;
    private Integer iduser;
    private String text;

    public Comentario(Integer idPubli, Integer idUser, String text) {
        this.idpubli = idPubli;
        iduser = idUser;
        this.text = text;
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
