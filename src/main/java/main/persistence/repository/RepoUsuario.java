package main.persistence.repository;

import main.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoUsuario extends JpaRepository<Usuario, Integer> {

    public Usuario findByemail(String email);
    List<Usuario> findBynameContainingIgnoreCase(String name);
}
