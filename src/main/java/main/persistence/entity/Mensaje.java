package main.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.NoPermissionException;
import javax.persistence.*;

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
    private void preventUnauthorizedRemove() throws NoPermissionException {

        Integer deleterId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!deleterId.equals(iduser1))
            throw new NoPermissionException("You're not allowed to do that");
    }

}
