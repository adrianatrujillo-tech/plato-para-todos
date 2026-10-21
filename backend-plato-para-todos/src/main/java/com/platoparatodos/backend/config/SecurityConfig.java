package com.platoparatodos.backend.config;

import com.platoparatodos.backend.repository.UsuarioRepository;
import com.platoparatodos.backend.service.TokenService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig {
  @Bean
  PasswordEncoder passwords() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetails(UsuarioRepository repo) {
    return correo -> {
      var u =
          repo.findByCorreo(correo)
              .orElseThrow(() -> new UsernameNotFoundException("Cuenta no encontrada"));
      return User.withUsername(u.getCorreo())
          .password(u.getPassword())
          .roles(u.getRol().name())
          .build();
    };
  }

  @Bean
  SecurityFilterChain security(HttpSecurity http, TokenService tokens, UsuarioRepository usuarios)
      throws Exception {
    // El navegador envía un bearer token explícito; no usamos cookies de autenticación.
    http.addFilterBefore(new AuthRateLimitFilter(), UsernamePasswordAuthenticationFilter.class)
        .csrf(c -> c.disable())
        .cors(c -> {})
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            a ->
                a.requestMatchers("/api/auth/login", "/api/auth/registro", "/api/health")
                    .permitAll()
                    .requestMatchers("/api/usuarios/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/api/**")
                    .authenticated()
                    .anyRequest()
                    .permitAll())
        .exceptionHandling(
            e ->
                e.authenticationEntryPoint(
                        (req, res, x) -> json(res, 401, "Inicia sesión para continuar."))
                    .accessDeniedHandler(
                        (req, res, x) -> json(res, 403, "No tienes permiso para esta operación.")))
        .addFilterBefore(
            new OncePerRequestFilter() {
              protected void doFilterInternal(
                  HttpServletRequest req, HttpServletResponse res, FilterChain chain)
                  throws ServletException, IOException {
                String h = req.getHeader("Authorization");
                if (h != null && h.startsWith("Bearer ")) {
                  Long id = tokens.usuario(h.substring(7));
                  if (id != null)
                    usuarios
                        .findById(id)
                        .filter(u -> u.isActivo())
                        .ifPresent(
                            u ->
                                SecurityContextHolder.getContext()
                                    .setAuthentication(
                                        new UsernamePasswordAuthenticationToken(
                                            u.getId(),
                                            null,
                                            List.of(
                                                new SimpleGrantedAuthority(
                                                    "ROLE_" + u.getRol().name())))));
                }
                chain.doFilter(req, res);
              }
            },
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  private static void json(HttpServletResponse res, int status, String message) throws IOException {
    res.setStatus(status);
    res.setContentType("application/json;charset=UTF-8");
    res.getWriter().write("{\"message\":\"" + message + "\"}");
  }
}
