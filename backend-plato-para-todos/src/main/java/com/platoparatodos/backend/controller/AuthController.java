package com.platoparatodos.backend.controller;

import com.platoparatodos.backend.dto.AuthResponse;
import com.platoparatodos.backend.dto.LoginRequest;
import com.platoparatodos.backend.dto.UsuarioRequest;
import com.platoparatodos.backend.dto.UsuarioResponse;
import com.platoparatodos.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;
  private final com.platoparatodos.backend.service.TokenService tokens;

  public AuthController(
      AuthService authService, com.platoparatodos.backend.service.TokenService tokens) {
    this.tokens = tokens;
    this.authService = authService;
  }

  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody LoginRequest request) {
    return authService.login(request);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(
      @org.springframework.web.bind.annotation.RequestHeader(
              value = "Authorization",
              required = false)
          String h) {
    if (h != null && h.startsWith("Bearer ")) tokens.revocar(h.substring(7));
  }

  @PostMapping("/registro")
  @ResponseStatus(HttpStatus.CREATED)
  public UsuarioResponse registrar(@Valid @RequestBody UsuarioRequest request) {
    return authService.registrar(request);
  }
}
