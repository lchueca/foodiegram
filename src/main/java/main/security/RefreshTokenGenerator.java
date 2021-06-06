package main.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.persistence.entity.Refreshtoken;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoRefreshtoken;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RefreshTokenGenerator {

    @Autowired
    RepoRefreshtoken repoRefresh;

    @Autowired
    RepoUsuario repoUser;



    @Value("${jwt.refresh.secret}")
    private String secretKey;


    public String buildToken(String username, int minutes) {

        Usuario user = repoUser.findByName(username);


        minutes *= 60000;

        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + minutes))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        Refreshtoken dbToken = repoRefresh.findByUserid(user.getId());

        if (dbToken != null)
            dbToken.setExpiredate(new Date(System.currentTimeMillis() + minutes));


        else
            dbToken = new Refreshtoken(user.getId(), new Date(System.currentTimeMillis() + minutes));


        repoRefresh.save(dbToken);

        return token;
    }

}
