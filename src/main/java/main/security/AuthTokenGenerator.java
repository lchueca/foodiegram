package main.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.persistence.entity.Jwtoken;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoJwtoken;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthTokenGenerator {

    @Autowired
    private RepoJwtoken repoJwtoke;

    @Autowired
    private RepoUsuario repoUser;


    @Value("${jwt.auth.secret}")
    private String secretKey;


    public String buildToken(String username, int minutes) {

        Usuario user = repoUser.findByName(username);
        return getToken(user.getId(), minutes, user.getRole().toString());
    }

    public String buildToken(Integer userID, int minutes) {

        Usuario user = repoUser.findOne(userID);
        return getToken(user.getId(), minutes, user.getRole().toString());
    }

    private String getToken(Integer userID, int minutes, String role) {

        minutes *= 60000;

        Jwtoken dbToken = repoJwtoke.findByUserid(userID);

        if(dbToken!= null)
            dbToken.setExpiredate(new Date(System.currentTimeMillis() + minutes));

        else
            dbToken = new Jwtoken(userID, new Date(System.currentTimeMillis() + minutes));


        repoJwtoke.save(dbToken);

        return  Jwts.builder()
                .setSubject(userID.toString())
                .claim("rol", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + minutes))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
    }

}

