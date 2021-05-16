package main.persistence.repository;

import main.persistence.entity.PubliJOINUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepoPubliJOINUser extends JpaRepository<PubliJOINUser, Integer> {

    @Query(value = "SELECT p.id, u.id as userid, u.name, u.image as userimage, " +
            "p.text, p.image, p.localization, p.media, p.numerototalval FROM usuario AS u JOIN publicacion AS p " +
            "ON u.id = p.iduser WHERE p.text LIKE %?1%", nativeQuery = true)
    List<PubliJOINUser> findByTag(String tag);
}
