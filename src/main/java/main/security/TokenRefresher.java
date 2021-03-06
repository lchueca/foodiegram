package main.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import main.persistence.entity.Refreshtoken;
import main.persistence.repository.RepoRefreshtoken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenRefresher {

    @Value("${jwt.refresh.secret}")
    private String secretKey;

    @Autowired
    RepoRefreshtoken repo;

    @Autowired
    AuthTokenGenerator generator;

    public String refresh(String token) throws ExpiredJwtException, MalformedJwtException {

        Claims claims =  Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
        Refreshtoken lastToken = repo.findByUserid(Integer.parseInt(claims.getSubject()));

        if (lastToken != null && claims.getExpiration().compareTo(lastToken.getExpiredate()) < 0)
            throw new ExpiredJwtException(null, claims, "A new token for this user has been created");

        return generator.buildToken(Integer.parseInt(claims.getSubject()),  30);



    }

}
