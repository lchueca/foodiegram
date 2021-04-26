package main.persistence.repository;

import main.persistence.entity.Publicacion;
import main.persistence.proyecciones.PreviewPublicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {


    public List<PreviewPublicacion> findByiduser(Integer id);




}