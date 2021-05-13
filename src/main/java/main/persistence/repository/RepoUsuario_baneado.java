package main.persistence.repository;

import main.persistence.entity.Mensaje;
import main.persistence.entity.Usuario_baneado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface RepoUsuario_baneado extends JpaRepository<Usuario_baneado, Integer> {

    public Usuario_baneado findById(Integer id);
}
