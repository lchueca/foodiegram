package main.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.persistence.entity.Jwtoken;
import main.persistence.entity.Role;
import main.persistence.entity.RoleEnum;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoJwtoken;

import main.persistence.repository.RepoRole;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JWTokenGenerator {

    @Autowired
    RepoJwtoken repoJwtoke;

    @Autowired
    RepoUsuario repoUser;

    @Autowired
    RepoRole repoRole;

    @Value("${jwt.secret}")
    private String secretKey;


    public String buildToken(String username, int minutes) {

        Usuario user = repoUser.findByName(username);
        List<RoleEnum> roles = repoRole.findByIduser(user.getId()).stream().map((Role::getRole)).collect(Collectors.toList());

        minutes *= 60000;

        String token = Jwts.builder()
        .setSubject(user.getId().toString()).claim("roles", roles)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + minutes))
        .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        Jwtoken tokens = repoJwtoke.findByUserid(user.getId());

        if(tokens!= null)
            tokens.setExpiredate(new Date(System.currentTimeMillis() + minutes));


        else
           tokens = new Jwtoken(user.getId(), new Date(System.currentTimeMillis() + minutes));


        repoJwtoke.save(tokens);

        return "Bearer " + token;

    }

}

