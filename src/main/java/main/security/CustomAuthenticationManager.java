//package main.security;
//
//
//import main.persistence.entity.Usuario;
//import main.persistence.repository.RepoUsuario;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
////@Component
//public class CustomAuthenticationManager implements AuthenticationManager {
//
//    @Autowired
//    RepoUsuario userRepo;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws BadCredentialsException, DisabledException {
//        String username = authentication.getPrincipal() + "";
//        String password = authentication.getCredentials() + "";
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        Usuario usuario = userRepo.findByName(username);
//
//        if (usuario == null || !encoder.matches(password, usuario.getPasswd()))
//            throw new BadCredentialsException("1000");
//
//        else if (!usuario.isEnabled())
//            throw new DisabledException("1001");
//
//        else
//            return new UsernamePasswordAuthenticationToken(username, password, null);
//
//
//    }
//}
