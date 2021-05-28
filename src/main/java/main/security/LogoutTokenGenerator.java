package main.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogoutTokenGenerator {

    @Value("${jwt.logout.secret}")
    private String secretKey;

    @Autowired
    private RepoUsuario repo;

    public String getToken(String username) {

        Usuario user = repo.findByName(username);

        return  Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
    }
}
