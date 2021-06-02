package main.persistence.repository;

import main.persistence.entity.Comentario;
import main.persistence.entity.ComentarioJOINUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepoComentarioJOINUser extends JpaRepository<ComentarioJOINUser, Integer> {

    @Query(value="SELECT * FROM comentario join usuario on comentario.iduser = usuario.id where comentario.idpubli = ?1",nativeQuery = true)
    public List<ComentarioJOINUser> findByIdpubliOrderByIdAsc(Integer idpubli);


}
