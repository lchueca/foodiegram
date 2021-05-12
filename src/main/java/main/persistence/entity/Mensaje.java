package main.persistence.entity;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.NoPermissionException;
import javax.persistence.*;

@Entity

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

    protected Mensaje() {}

    public Integer getId() {
        return id;
    }

    public Integer getIdUser1() {
        return iduser1;
    }

    public Integer getIdUser2() {
        return iduser2;
    }

    public String getText() {
        return text;
    }


    @PreRemove
    private void preventUnauthorizedRemove() throws NoPermissionException {

        Integer deleterId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!deleterId.equals(iduser1))
            throw new NoPermissionException("You're not allowed to do that");
    }

}
