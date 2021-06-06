package main.persistence.repository;

import main.persistence.entity.ColabJOINUser;
import main.persistence.entity.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoColaborador extends JpaRepository<Colaborador, Integer> {


}
