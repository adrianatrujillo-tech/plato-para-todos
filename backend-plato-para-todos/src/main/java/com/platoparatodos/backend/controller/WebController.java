package com.platoparatodos.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
  @GetMapping({
    "/login",
    "/registro",
    "/registro/**",
    "/donador",
    "/donador/**",
    "/beneficiario",
    "/beneficiario/**",
    "/admin",
    "/admin/**"
  })
  public String spa() {
    return "forward:/index.html";
  }
}
