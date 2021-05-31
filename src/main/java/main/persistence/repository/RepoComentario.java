package main.persistence.repository;

import main.persistence.entity.ColabJOINUser;
import main.persistence.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoComentario extends JpaRepository<Comentario, Integer> {

    @Query(value="SELECT * FROM comentario join usuario on comentario.iduser = usuario.id where comentario.idpubli = ?1"
            ,nativeQuery = true)
    public List<Comentario> findByIdpubliOrderByIdAsc(Integer idpubli);


    public List<Comentario> findByIduser(Integer iduser);
}
