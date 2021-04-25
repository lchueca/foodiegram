package main.persistence.repository;

import main.persistence.IDs.IDvaloracion;
import main.persistence.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoValoracion extends JpaRepository<Valoracion, IDvaloracion>{}
