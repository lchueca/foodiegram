package main.persistence.repository;

import main.persistence.entity.ColabJOINUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoColabJOINUser extends JpaRepository<ColabJOINUser, Integer> {

    @Query(value = "SELECT * FROM colaborador AS c, (SELECT * FROM usuario WHERE usuario.name LIKE %?1%) AS u WHERE c.id = u.id", nativeQuery = true)
    List<ColabJOINUser> findByUsername(String colabname);
    @Query(value = "SELECT * FROM (SELECT * FROM colaborador WHERE colaborador.origin LIKE %?1%) AS c, usuario AS u WHERE c.id = u.id", nativeQuery = true)
    List<ColabJOINUser> findByOrigin(String origin);
    @Query(value = "SELECT * FROM (SELECT * FROM colaborador WHERE colaborador.type LIKE %?1%) AS c, usuario AS u WHERE c.id = u.id", nativeQuery = true)
    List<ColabJOINUser> findByType(String type);
}
