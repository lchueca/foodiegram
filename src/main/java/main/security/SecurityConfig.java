package main.security;

import main.persistence.repository.RepoJwtoken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RepoJwtoken repoTokens;

    @Value("${jwt.auth.secret}")
    private String authSecret;

    @Value("${jwt.logout.secret}")
    private String logoutSecret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.cors().and().csrf().disable();
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();


        http.authorizeRequests().antMatchers(HttpMethod.POST, "*").hasAnyRole("USER", "COL", "MOD", "ADMIN")

                .regexMatchers("\\/events\\/[a-z]+$").hasAnyRole("USER", "COL", "MOD", "ADMIN")
                .antMatchers("/events").hasAnyRole("COL", "MOD", "ADMIN")
                .regexMatchers("\\/events\\/\\d+$").hasAnyRole("COL", "MOD", "ADMIN")



                .antMatchers("/mod/**").hasAnyRole("ADMIN", "MOD")
                .antMatchers("/admin/**").hasRole("ADMIN");

        http.addFilterAfter(new JwtTokenFilter(repoTokens, authSecret, logoutSecret), UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()

                // Controller usuario
                .antMatchers("/css/**")
                .antMatchers("/users/**")
                .antMatchers("/pruebas")
                .antMatchers("/pruebas/postLogin")
                .regexMatchers("/pruebas/problems/\\d+")
                .antMatchers("/pruebas/register")
                .antMatchers("/pruebas/postRegister")
                // Controller publicacion
                .regexMatchers(HttpMethod.GET, "\\/posts\\/\\w+$")
                .regexMatchers(HttpMethod.GET, "\\/posts\\/\\w+\\/ratings$")
                .regexMatchers(HttpMethod.GET, "\\/posts\\/\\w+\\/ratings/\\d+$")
                .regexMatchers(HttpMethod.GET, "\\/posts\\/\\w+\\/comments$")

                // Controller search
                .antMatchers("/search/**")

                // Controller event
                .antMatchers("/event/**");

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}