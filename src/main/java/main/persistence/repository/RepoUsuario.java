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
    List<Usuario> findByEnabled(boolean enabled);
    List<Usuario> findBynameContainingIgnoreCase(String name);

    @Query(value = "select * from usuario join numvalpubli on id = iduser order by numpubli desc limit 15", nativeQuery = true)
    List<Usuario> findByPopuPubli();

    @Query(value = "select * from usuario join numvalpubli on id = iduser order by numval desc limit 15", nativeQuery = true)
    List<Usuario> findByPopuVal();

    @Query (value="select u1 " +
            "from amigo a1 join usuario u1 on a1.iduser2 = u1.id " +
            "where not exists (select a5" +
            "                  from amigo a5 " +
            "                  where iduser1 = ?1 and iduser2 = a1.iduser2) " +
            "      and exists (select a2" +
            "                  from amigo a2 " +
            "                  where a2.iduser1 = ?1  " +
            "                  and exists (select a3 " +
            "                              from amigo a3 " +
            "                              where a3.iduser1 = a2.iduser2 and a3.iduser2 = a1.iduser2)) " +
            "group by u1.id, u1.name " +
            "order by count(u1.id)", nativeQuery = true)
    List<Usuario> findFollowedByFriends(Integer userID);

}

