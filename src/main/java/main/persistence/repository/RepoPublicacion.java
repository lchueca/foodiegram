package main.persistence.repository;

import main.persistence.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {
    public List<Publicacion> findByiduser(Integer iduser);

}