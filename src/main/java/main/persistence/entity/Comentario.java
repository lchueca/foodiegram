package main.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.NoPermissionException;
import javax.persistence.*;

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

        if (!deleterId.equals(iduser))
            throw new NoPermissionException("You're not allowed to do that");
    }
}
