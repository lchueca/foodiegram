package main.persistence.repository;

import main.persistence.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {

    @Query(value="select id, image from publicacion where id_user  = :idx", nativeQuery = true)
    public List<Publicacion> findByiduser(@Param("idx") Integer idx);

}