package main.security;


import io.jsonwebtoken.*;
import main.persistence.entity.Jwtoken;
import main.persistence.entity.RoleEnum;
import main.persistence.repository.RepoJwtoken;
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

    private final String authSecret;
    private final String logoutSecret;

    private RepoJwtoken repoTokens;

    public JwtTokenFilter(RepoJwtoken repoToken, String authSecret, String logoutSecret) {
        this.repoTokens = repoToken;
        this.authSecret = authSecret;
        this.logoutSecret = logoutSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {


        try {
            // Comprueba si se ha dado un token valido. Se lanza una UnsupportedJwtException si no es valido.

            Cookie logoutCookie = getCookie(request, "loggedOut");
            if (logoutCookie == null)
                throw new MalformedJwtException("You need to log in to access this resource");

            checkLogoutCookie(logoutCookie);


            Cookie authCookie = getCookie(request, "authToken");

            if (authCookie == null)
                throw new MalformedJwtException("You need to log in to access this resource");

            Claims claims = validateAuthToken(authCookie);
            setUpSpringAuthentication(claims);


            chain.doFilter(request, response);



        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    private void checkLogoutCookie(Cookie cookie) throws UnsupportedJwtException, MalformedJwtException {

        String jwToken = cookie.getValue();
        Jwts.parser().setSigningKey(logoutSecret.getBytes()).parseClaimsJws(jwToken).getBody();

    }

    private Claims validateAuthToken(Cookie cookie) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException {

        String jwtToken = cookie.getValue();

        Claims claims =  Jwts.parser().setSigningKey(authSecret.getBytes()).parseClaimsJws(jwtToken).getBody();

        Jwtoken lastToken = repoTokens.findByUserid(Integer.parseInt(claims.getSubject()));

        if (lastToken != null && claims.getExpiration().compareTo(lastToken.getExpiredate()) < 0)
            throw new ExpiredJwtException(null, claims, "Someone logged in from another computer");


        return claims;
    }


    private void setUpSpringAuthentication(Claims claims) {

        List<RoleEnum> roles = new ArrayList<>();
        roles.add(RoleEnum.valueOf(claims.get("rol").toString()));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,roles );

        SecurityContextHolder.getContext().setAuthentication(auth);


    }


    private Cookie getCookie(HttpServletRequest request, String name) {

        for (Cookie cookie : request.getCookies())
            if(cookie.getName().equals(name))
                return cookie;

        return null;
    }


}
