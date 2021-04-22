package main.persistence.repository;

import main.persistence.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {}