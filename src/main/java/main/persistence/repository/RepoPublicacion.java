package main.persistence.repository;

import main.persistence.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {

     List<Publicacion> findByIduser(Integer id);

     @Query (value="SELECT * FROM publicacion " +
             "ORDER BY publicacion.media DESC " +
             "LIMIT 0,50 " +
             "where publicacion.fecha > date_sub(NOW(), interval :q :interval)"
             ,nativeQuery = true)
     List<Publicacion> discoverBestRated(@Param("q")Integer q, @Param("interval")String interval);

     @Query (value="SELECT * FROM publicacion "+
             "ORDER BY publicacion.numerototalval DESC "+
             "LIMIT 0,50 "+
             "where publicacion.fecha > date_sub(NOW(), interval ?1 ?2)"
             ,nativeQuery = true)
     List<Publicacion> discoverMostRated(Integer quantity, String interval);

     @Query(value="SELECT publicacion.* " +
             "FROM publicacion JOIN amigo ON amigo.iduser2 = publicacion.iduser " +
             "WHERE  amigo.iduser1=?1 "+
             "ORDER BY publicacion.fecha DESC",nativeQuery = true)
     List<Publicacion> findbyFriend(Integer user1);


}