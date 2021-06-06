package main.persistence.repository;

import main.persistence.entity.ColabJOINUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoColabJOINUser extends JpaRepository<ColabJOINUser, Integer> {

    @Query(value = "SELECT * FROM colaborador AS c, (SELECT * FROM usuario WHERE usuario.name LIKE %?1%) AS u WHERE c.id = u.id", nativeQuery = true)
    List<ColabJOINUser> findByUsername(String colabname);
    @Query(value = "SELECT * FROM (SELECT * FROM colaborador WHERE colaborador.origin LIKE %?1%) AS c, usuario AS u WHERE c.id = u.id", nativeQuery = true)
    List<ColabJOINUser> findByOrigin(String origin);
    @Query(value = "SELECT * FROM (SELECT * FROM colaborador WHERE colaborador.type LIKE %?1%) AS c, usuario AS u WHERE c.id = u.id", nativeQuery = true)
    List<ColabJOINUser> findByType(String type);


    @Query(value="SELECT * FROM colaborador JOIN usuario ON colaborador.id=usuario.id "+
            "WHERE IFNULL(colaborador.ciudad, 'xxx') LIKE ?2 and IFNULL(colaborador.pais, 'xxx') LIKE ?1 " +
            "ORDER BY colaborador.vip DESC,colaborador.money DESC "+
            "LIMIT 0,50 "
            ,nativeQuery = true)
    List<ColabJOINUser>  descubrirCollab (String country, String city);


}
