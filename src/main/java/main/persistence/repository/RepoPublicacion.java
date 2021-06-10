package main.persistence.repository;

import main.persistence.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {

     List<Publicacion> findByIduserOrderByIdDesc(Integer id);

     @Query (value="SELECT * FROM publicacion "+
             "where ( ?1 = -1 or publicacion.fecha > date_sub(NOW(), interval ?1 DAY) ) and (IFNULL(publicacion.ciudad, 'xxx') LIKE ?3 and IFNULL(publicacion.pais LIKE ?2, 'xxx'))" +
             "ORDER BY publicacion.media DESC "+
             "LIMIT 0,50 "
             ,nativeQuery = true)
     List<Publicacion> bestRated(Integer amount, String country, String city);

     @Query (value="SELECT * FROM publicacion "+
             "where ( ?1 = -1 or publicacion.fecha > date_sub(NOW(), interval ?1 DAY) ) and (IFNULL(publicacion.ciudad, 'xxx') LIKE ?3 and IFNULL(publicacion.pais LIKE ?2, 'xxx'))" +
             "ORDER BY publicacion.numerototalval DESC "+
             "LIMIT 0,50 "

             ,nativeQuery = true)
     List<Publicacion> mostRated(Integer amount, String pais, String ciudad);

     @Query(value="SELECT publicacion.* " +
             "FROM publicacion JOIN amigo ON amigo.iduser2 = publicacion.iduser " +
             "WHERE  amigo.iduser1=?1 "+
             "ORDER BY publicacion.fecha DESC",nativeQuery = true)
     List<Publicacion> fromFriends(Integer user1);





}