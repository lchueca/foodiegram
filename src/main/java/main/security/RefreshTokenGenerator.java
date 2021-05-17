package main.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.persistence.entity.*;
import main.persistence.repository.RepoJwtoken;
import main.persistence.repository.RepoRefreshtoken;
import main.persistence.repository.RepoRole;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RefreshTokenGenerator {

    @Autowired
    RepoRefreshtoken repoRefresh;

    @Autowired
    RepoUsuario repoUser;



    @Value("${jwt.secret}")
    private String secretKey;


    public String buildToken(String username, int minutes) {

        Usuario user = repoUser.findByName(username);


        minutes *= 60000;

        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + minutes))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        Refreshtoken tokens =repoRefresh.findByUserid(user.getId());

        if (tokens != null)
            tokens.setExpiredate(new Date(System.currentTimeMillis() + minutes));


        else
            tokens = new Refreshtoken(user.getId(), new Date(System.currentTimeMillis() + minutes));


        repoRefresh.save(tokens);

        return token;
    }

}
