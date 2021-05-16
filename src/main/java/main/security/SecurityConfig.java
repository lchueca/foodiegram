package main.security;

import main.persistence.entity.RoleEnum;
import main.persistence.entity.Usuario;
import main.persistence.repository.RepoJwtoken;
import main.persistence.repository.RepoUsuario;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RepoJwtoken repoTokens;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.cors().and().csrf().disable();
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();


        http.authorizeRequests().antMatchers(HttpMethod.POST, "*").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN");

        http.addFilterAfter(new JwtTokenFilter(repoTokens, secretKey), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()

                // Controller usuario
                .antMatchers("/users/**")

                // Controller publicacion
                .regexMatchers(HttpMethod.GET, "\\/posts\\/\\w+$")
                .regexMatchers(HttpMethod.GET, "\\/posts\\/\\w+\\/ratings$")
                .regexMatchers(HttpMethod.GET, "\\/posts\\/\\w+\\/comments$")

                // Controller search
                .antMatchers("/search/**");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}