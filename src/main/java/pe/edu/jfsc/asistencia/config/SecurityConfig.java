package pe.edu.jfsc.asistencia.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.SecurityFilterChain;
import pe.edu.jfsc.asistencia.repository.AppUserRepository;

@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService userDetailsService(AppUserRepository repo){
   return username -> repo.findByUsername(username).map(u -> User.withUsername(u.getUsername()).password(u.getPasswordHash()).roles(u.getRole().name()).disabled(!u.isEnabled()).build()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
 }
 @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
   http.authorizeHttpRequests(auth -> auth
      .requestMatchers("/css/**","/js/**","/login","/error").permitAll()
      .requestMatchers("/users/**").hasRole("ADMINISTRADOR")
      .requestMatchers("/students/new","/students/save","/students/delete/**","/sections/new","/sections/save","/sections/delete/**").hasRole("ADMINISTRADOR")
      .requestMatchers("/attendance/**").hasAnyRole("DOCENTE","ADMINISTRADOR")
      .requestMatchers("/justifications/**").hasAnyRole("DOCENTE","ADMINISTRADOR")
      .requestMatchers("/reports/**").hasAnyRole("DOCENTE","ADMINISTRADOR","DIRECTOR")
      .anyRequest().hasAnyRole("DOCENTE","ADMINISTRADOR","DIRECTOR"))
     .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/dashboard", true).permitAll())
     .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
     .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));
   return http.build();
 }
}
