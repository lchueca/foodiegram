package main.persistence.repository;

import main.persistence.entity.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoColaborador extends JpaRepository<Colaborador, Integer> {

    Colaborador findById(Integer id);
}
