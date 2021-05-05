package main.persistence.repository;

import main.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RepoUsuario extends JpaRepository<Usuario, Integer> {

    public Usuario findByemail(String email);
    public Usuario findById(Integer id);
    public Usuario findByName(String name);
    List<Usuario> findBynameContainingIgnoreCase(String name);
}

