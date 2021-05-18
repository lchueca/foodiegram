package main.persistence.repository;

import main.persistence.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoEvento extends JpaRepository<Evento, Integer> {

    Evento findById(Integer id);
    List<Evento> findByIdcolab(Integer idcolab);
}