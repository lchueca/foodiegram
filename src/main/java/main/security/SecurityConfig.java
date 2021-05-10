package main.security;

import main.persistence.entity.Usuario;
import main.persistence.repository.RepoUsuario;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RepoUsuario userRepo;
    private final JwtTokenFilter filter;

    public SecurityConfig(RepoUsuario userRepo, JwtTokenFilter filter) {
        this.userRepo = userRepo;
        this.filter = filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            Usuario user = userRepo.findByName(username);
            return new UserDetailsImpl(user.getName(), user.getPasswd(), user.isEnabled());
        });

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();


        http.csrf().disable()
                .addFilterAfter(new JwtTokenFilter(userRepo), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/users/register").permitAll()
                .antMatchers(HttpMethod.GET, "/users/verify/*").permitAll()
                .anyRequest().authenticated();
    }



}