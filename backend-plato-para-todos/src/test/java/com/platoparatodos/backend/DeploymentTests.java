package com.platoparatodos.backend;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platoparatodos.backend.repository.*;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DeploymentTests {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper json;
  @Autowired UsuarioRepository usuarios;

  private String registro(String correo, String rol) throws Exception {
    return mvc.perform(
            post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    json.writeValueAsString(
                        Map.of(
                            "nombre",
                            "Prueba",
                            "correo",
                            correo,
                            "password",
                            "Password123!",
                            "rol",
                            rol))))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();
  }

  private String login(String correo) throws Exception {
    return json.readTree(
            mvc.perform(
                    post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            json.writeValueAsString(
                                Map.of("correo", correo, "password", "Password123!"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString())
        .get("token")
        .asText();
  }

  @Test
  void healthYNoHayCuentasDemo() throws Exception {
    mvc.perform(get("/api/health"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value("ok"));
    assertThat(usuarios.findByCorreo("admin@test.com")).isEmpty();
  }

  @Test
  void apiRequiereTokenReal() throws Exception {
    mvc.perform(get("/api/donaciones")).andExpect(status().isUnauthorized());
    mvc.perform(get("/api/donaciones").header("Authorization", "Bearer demo-inventado"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void registroProtegePasswordYNoPermiteAdmin() throws Exception {
    registro("hash@example.com", "DONADOR");
    assertThat(usuarios.findByCorreo("hash@example.com").get().getPassword())
        .startsWith("$2")
        .doesNotContain("Password123!");
    mvc.perform(
            post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    json.writeValueAsString(
                        Map.of(
                            "nombre",
                            "No admin",
                            "correo",
                            "admin-nuevo@example.com",
                            "password",
                            "Password123!",
                            "rol",
                            "ADMIN"))))
        .andExpect(status().isForbidden());
  }

  @Test
  void tokenPermiteAccesoYCerrarSesionLoRevoca() throws Exception {
    registro("sesion@example.com", "DONADOR");
    var token = login("sesion@example.com");
    mvc.perform(get("/api/donaciones").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
    mvc.perform(post("/api/auth/logout").header("Authorization", "Bearer " + token))
        .andExpect(status().isNoContent());
    mvc.perform(get("/api/donaciones").header("Authorization", "Bearer " + token))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void unaCuentaNoPuedeEditarLaDonacionDeOtra() throws Exception {
    var id = json.readTree(registro("propietario@example.com", "DONADOR")).get("id").asLong();
    var token = login("propietario@example.com");
    var payload =
        new HashMap<String, Object>(
            Map.of(
                "titulo",
                "Frutas",
                "categoria",
                "Frutas",
                "cantidad",
                5,
                "unidad",
                "Kg",
                "fechaVencimiento",
                LocalDate.now().plusDays(2).toString(),
                "modalidadEntrega",
                "RETIRO",
                "donadorId",
                id));
    var result =
        mvc.perform(
                post("/api/donaciones")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json.writeValueAsString(payload)))
            .andExpect(status().isCreated())
            .andReturn();
    var donacionId = json.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    registro("otra@example.com", "DONADOR");
    var otra = login("otra@example.com");
    mvc.perform(delete("/api/donaciones/" + donacionId).header("Authorization", "Bearer " + otra))
        .andExpect(status().isForbidden());
    mvc.perform(get("/api/donaciones?donadorId=" + id).header("Authorization", "Bearer " + otra))
        .andExpect(status().isForbidden());
    mvc.perform(get("/api/usuarios").header("Authorization", "Bearer " + otra))
        .andExpect(status().isForbidden());
  }
}
