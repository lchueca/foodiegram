package main.persistence.repository;

import main.persistence.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoEvento extends JpaRepository<Evento,Integer> {
}