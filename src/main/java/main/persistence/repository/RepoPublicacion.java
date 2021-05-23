package main.persistence.repository;

import main.persistence.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {

     List<Publicacion> findByIduser(Integer id);

     @Query (value="SELECT * FROM publicacion"+
             "Order By publicacion.media DESC"+
            "LIMIT 0,50",nativeQuery = true)
     List<Publicacion> findByPopularity();

     @Query(value="Select publicacion.iduser,publicacion.text,publicacion.image,publicacion.fecha,publicacion.ciudad,publicacion.pais,publicacion.media " +
             "from publicacion join amigo on amigo.iduser2 = publicacion.iduser " +
             "where  amigo.iduser1=user1"+
             "order by publicacion.fecha DESC",nativeQuery = true)

     List<Publicacion> findbyFriend(Integer user1);
}