package main.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.persistence.entity.Jwtoken;
import main.persistence.repository.RepoJwtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTokenGenerator {

    @Autowired
    RepoJwtoken repoJwtoke;


    private final String secretKey = "MamiChanXFungus";

    public String buildToken(String username, String password) {

        String token = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1800000))
        .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        Jwtoken tokens= repoJwtoke.findByUsername(username);

        if(tokens!= null)
            tokens.setExpiredate(new Date(System.currentTimeMillis() + 1800000));


        else
           tokens = new Jwtoken(username, new Date(System.currentTimeMillis() + 1800000));


        repoJwtoke.save(tokens);

        return "Bearer " + token;

    }

}

