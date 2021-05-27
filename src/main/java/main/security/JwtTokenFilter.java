package main.security;


import io.jsonwebtoken.*;
import main.persistence.entity.Jwtoken;
import main.persistence.entity.RoleEnum;
import main.persistence.repository.RepoJwtoken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JwtTokenFilter extends OncePerRequestFilter {

    private final String SECRET;

    private RepoJwtoken repoTokens;

    public JwtTokenFilter(RepoJwtoken repoToken, String secret) {
        this.repoTokens = repoToken;
        this.SECRET = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {


        try {
            // Comprueba si se ha dado un token valido. Se lanza una UnsupportedJwtException si no es valido.
            Cookie cookie = checkJWTToken(request, response);
            Claims claims = validateToken(cookie);
            setUpSpringAuthentication(claims);


            chain.doFilter(request, response);



        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    private Claims validateToken(Cookie cookie) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException{

        String jwtToken = cookie.getValue();

        Claims claims =  Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();

        Jwtoken lastToken = repoTokens.findByUserid(Integer.parseInt(claims.getSubject()));

        if (lastToken != null && claims.getExpiration().compareTo(lastToken.getExpiredate()) < 0)
            throw new ExpiredJwtException(null, claims, "A new token for this user has been created");


        return claims;
    }


    private void setUpSpringAuthentication(Claims claims) {

        List<RoleEnum> roles = new ArrayList<>();
        roles.add(RoleEnum.valueOf(claims.get("rol").toString()));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,roles );

        SecurityContextHolder.getContext().setAuthentication(auth);


    }


    private Cookie checkJWTToken(HttpServletRequest request, HttpServletResponse res) throws UnsupportedJwtException {

          Cookie[] cookies= request.getCookies();

          if(cookies==null)
              throw new UnsupportedJwtException("You must be logged in to access this resource.");

          for(Cookie cookie : cookies)
              if(cookie.getName().equals("authToken"))
                  return  cookie;

          throw new UnsupportedJwtException("You must be logged in to access this resource.");


    }


}
