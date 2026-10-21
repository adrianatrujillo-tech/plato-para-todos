package com.platoparatodos.backend.service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
  private record Sesion(Long usuarioId, Instant vence) {}

  private final Map<String, Sesion> sesiones = new ConcurrentHashMap<>();
  private final SecureRandom random = new SecureRandom();

  private String hash(String token) {
    try {
      return HexFormat.of()
          .formatHex(
              MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8)));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  public synchronized String crear(Long usuarioId) {
    sesiones.entrySet().removeIf(e -> e.getValue().vence().isBefore(Instant.now()));
    if (sesiones.size() >= 5000)
      throw new org.springframework.web.server.ResponseStatusException(
          org.springframework.http.HttpStatus.TOO_MANY_REQUESTS,
          "Demasiadas sesiones activas. Intenta más tarde.");
    byte[] bytes = new byte[32];
    random.nextBytes(bytes);
    String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    sesiones.put(hash(token), new Sesion(usuarioId, Instant.now().plusSeconds(43200)));
    return token;
  }

  public Long usuario(String token) {
    if (token.length() > 200) return null;
    var s = sesiones.get(hash(token));
    if (s == null) return null;
    if (s.vence().isBefore(Instant.now())) {
      sesiones.remove(hash(token));
      return null;
    }
    return s.usuarioId();
  }

  public void revocar(String token) {
    if (token != null && token.length() < 200) sesiones.remove(hash(token));
  }
}
