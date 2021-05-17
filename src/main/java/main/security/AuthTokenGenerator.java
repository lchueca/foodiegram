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
public class AuthTokenGenerator {

    @Autowired
    private RepoJwtoken repoJwtoke;

    @Autowired
    private RepoUsuario repoUser;

    @Autowired
    private RepoRole repoRole;

    @Value("${jwt.secret}")
    private String secretKey;


    public String buildToken(String username, int minutes) {

        Usuario user = repoUser.findByName(username);
        return buildToken(user.getId(), minutes);
    }

    public String buildToken(Integer userIdD, int minutes) {


        List<RoleEnum> roles = repoRole.findByIduser(userIdD).stream().map((Role::getRole)).collect(Collectors.toList());

        minutes *= 60000;

        Jwtoken tokens = repoJwtoke.findByUserid(userIdD);

        if(tokens!= null)
            tokens.setExpiredate(new Date(System.currentTimeMillis() + minutes));


        else
            tokens = new Jwtoken(userIdD, new Date(System.currentTimeMillis() + minutes));


        repoJwtoke.save(tokens);

        return  Jwts.builder()
                .setSubject(userIdD.toString())
                .claim("roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + minutes))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

    }





}

