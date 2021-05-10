package main.security;


import io.jsonwebtoken.*;
import main.persistence.entity.Jwtoken;
import main.persistence.repository.RepoJwtoken;
import main.persistence.repository.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String SECRET = "MamiChanXFungus";

    public JwtTokenFilter(RepoUsuario repo) {


    }

    @Autowired
    private RepoJwtoken repoTokens;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (checkJWTToken(request, response)) {
                Claims claims = validateToken(request);

                if (claims != null)
                    setUpSpringAuthentication(claims);

                else
                    SecurityContextHolder.clearContext();

            }
            chain.doFilter(request, response);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    private Claims validateToken(HttpServletRequest request) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException{
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer", "");

        Claims claims =  Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();

        Jwtoken lastToken = repoTokens.findByUsername(claims.getSubject());

        if (claims.getExpiration().compareTo(lastToken.getExpiredate()) < 0)
            return null;
        else
            return claims;
    }


    private void setUpSpringAuthentication(Claims claims) {
        // para cuando hagamos con admins y cosas chidas
        //List<String> authorities = (List) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                null);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer"))
            return false;
        return true;
    }


}
