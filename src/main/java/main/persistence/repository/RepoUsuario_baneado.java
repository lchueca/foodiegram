package main.persistence.repository;

import main.persistence.entity.Usuario_baneado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoUsuario_baneado extends JpaRepository<Usuario_baneado, Integer> {

}
