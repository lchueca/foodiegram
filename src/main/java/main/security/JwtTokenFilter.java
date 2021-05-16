package main.security;


import io.jsonwebtoken.*;
import main.persistence.entity.Jwtoken;
import main.persistence.entity.Role;
import main.persistence.entity.RoleEnum;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoJwtoken;
import main.persistence.repository.RepoUsuario;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;


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
            checkJWTToken(request, response);
            Claims claims = validateToken(request);
            setUpSpringAuthentication(claims);

            chain.doFilter(request, response);



        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    private Claims validateToken(HttpServletRequest request) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException{
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer", "");

        Claims claims =  Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();

        Jwtoken lastToken = repoTokens.findByUserid(Integer.parseInt(claims.getSubject()));

        if (lastToken != null && claims.getExpiration().compareTo(lastToken.getExpiredate()) < 0)
            throw new ExpiredJwtException(null, claims, "A new token for this user has been created");


        return claims;
    }


    private void setUpSpringAuthentication(Claims claims) {
        // para cuando hagamos con admins y cosas chidas
        //List<String> authorities = (List) claims.get("authorities");

        List<String> roles1 = (List<String>) claims.get("roles");
        List<RoleEnum> roles2 = roles1.stream().map(rol -> RoleEnum.valueOf(rol)).collect(Collectors.toList());;

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, roles2);

        SecurityContextHolder.getContext().setAuthentication(auth);


    }

    private void checkJWTToken(HttpServletRequest request, HttpServletResponse res) throws UnsupportedJwtException {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer"))
            throw new UnsupportedJwtException("You must be logged in to access this resource.");
    }


}
