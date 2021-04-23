package main.persistence.repository;

import main.persistence.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoEvento extends JpaRepository<Evento,Integer> {
}