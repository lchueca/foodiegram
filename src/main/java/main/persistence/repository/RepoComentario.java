package main.persistence.repository;

import main.persistence.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoComentario extends JpaRepository<Comentario, Integer> {

    public List<Comentario> findByidpubli(Integer idpubli);
    public List<Comentario> findByiduser(Integer iduser);
}
