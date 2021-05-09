package main.persistence.repository;

import main.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoUsuario extends JpaRepository<Usuario, Integer> {

    public Usuario findByEmail(String email);
    public Usuario findById(Integer id);
    public Usuario findByName(String name);
    List<Usuario> findBynameContainingIgnoreCase(String name);

    @Query(value = "select * from usuario join numvalpubli on id = iduser order by numpubli desc limit 15", nativeQuery = true)
    List<Usuario> findByPopuPubli();

    @Query(value = "select * from usuario join numvalpubli on id = iduser order by numval desc limit 15", nativeQuery = true)
    List<Usuario> findByPopuVal();
}

