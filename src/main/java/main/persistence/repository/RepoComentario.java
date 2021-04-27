package main.persistence.repository;

import main.persistence.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoComentario extends JpaRepository<Comentario, Integer> {

}
