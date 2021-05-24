package main.persistence.repository;

import main.persistence.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {

     List<Publicacion> findByIduser(Integer id);

     @Query (value="SELECT * FROM publicacion "+
             "ORDER BY publicacion.media DESC "+
            "LIMIT 0,50",nativeQuery = true)
     List<Publicacion> findByPopularity();

     @Query(value="SELECT publicacion.* " +
             "FROM publicacion JOIN amigo ON amigo.iduser2 = publicacion.iduser " +
             "WHERE  amigo.iduser1=?1 "+
             "ORDER BY publicacion.fecha DESC",nativeQuery = true)
     List<Publicacion> findbyFriend(Integer user1);


}