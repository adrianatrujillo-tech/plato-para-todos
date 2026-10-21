package com.platoparatodos.backend.service;

import com.platoparatodos.backend.dto.DonacionRequest;
import com.platoparatodos.backend.dto.DonacionResponse;
import com.platoparatodos.backend.dto.DonacionUpdateRequest;
import com.platoparatodos.backend.exception.OperacionInvalidaException;
import com.platoparatodos.backend.exception.RecursoNoEncontradoException;
import com.platoparatodos.backend.model.Donacion;
import com.platoparatodos.backend.model.EstadoDonacion;
import com.platoparatodos.backend.model.Rol;
import com.platoparatodos.backend.model.Usuario;
import com.platoparatodos.backend.repository.DonacionRepository;
import com.platoparatodos.backend.repository.SolicitudRepository;
import com.platoparatodos.backend.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DonacionService {
  @org.springframework.beans.factory.annotation.Autowired private AccesoService acceso;

  private final DonacionRepository donacionRepository;
  private final UsuarioRepository usuarioRepository;
  private final SolicitudRepository solicitudRepository;

  public DonacionService(
      DonacionRepository donacionRepository,
      UsuarioRepository usuarioRepository,
      SolicitudRepository solicitudRepository) {
    this.donacionRepository = donacionRepository;
    this.usuarioRepository = usuarioRepository;
    this.solicitudRepository = solicitudRepository;
  }

  public List<DonacionResponse> listarTodas() {
    var u = acceso.actual();
    if (u.getRol() == Rol.DONADOR) return listarPorDonador(u.getId());
    if (u.getRol() == Rol.BENEFICIARIO) return listarDisponibles();
    return donacionRepository.findAll().stream().map(DonacionResponse::from).toList();
  }

  public List<DonacionResponse> listarDisponibles() {
    return donacionRepository.findByEstado(EstadoDonacion.DISPONIBLE).stream()
        .map(DonacionResponse::from)
        .toList();
  }

  public List<DonacionResponse> listarPorDonador(Long donadorId) {
    acceso.propio(donadorId);
    return donacionRepository.findByDonadorId(donadorId).stream()
        .map(DonacionResponse::from)
        .toList();
  }

  public DonacionResponse obtenerPorId(Long id) {
    return DonacionResponse.from(buscarDonacion(id));
  }

  public DonacionResponse crear(DonacionRequest request) {
    acceso.rol(Rol.DONADOR);
    acceso.propio(request.donadorId());
    Usuario donador =
        usuarioRepository
            .findById(request.donadorId())
            .orElseThrow(() -> new RecursoNoEncontradoException("Donador no encontrado"));

    if (donador.getRol() != Rol.DONADOR) {
      throw new OperacionInvalidaException("El usuario indicado no tiene rol DONADOR");
    }

    Donacion donacion = new Donacion();
    donacion.setTitulo(request.titulo());
    donacion.setCategoria(request.categoria());
    donacion.setCantidad(request.cantidad());
    donacion.setUnidad(request.unidad());
    donacion.setFechaVencimiento(request.fechaVencimiento());
    donacion.setModalidadEntrega(request.modalidadEntrega());
    donacion.setDireccionRecojo(request.direccionRecojo());
    donacion.setHorarioDisponible(request.horarioDisponible());
    donacion.setDescripcion(request.descripcion());
    donacion.setObservaciones(request.observaciones());
    donacion.setDonador(donador);

    return DonacionResponse.from(donacionRepository.save(donacion));
  }

  public DonacionResponse editar(Long id, DonacionUpdateRequest request) {
    Donacion donacion = buscarDonacion(id);
    acceso.propio(donacion.getDonador().getId());

    if (donacion.getEstado() != EstadoDonacion.DISPONIBLE) {
      throw new OperacionInvalidaException("No se puede editar una donacion entregada");
    }

    donacion.setTitulo(request.titulo());
    donacion.setCategoria(request.categoria());
    donacion.setCantidad(request.cantidad());
    donacion.setUnidad(request.unidad());
    donacion.setFechaVencimiento(request.fechaVencimiento());
    donacion.setModalidadEntrega(request.modalidadEntrega());
    donacion.setDireccionRecojo(request.direccionRecojo());
    donacion.setHorarioDisponible(request.horarioDisponible());
    donacion.setDescripcion(request.descripcion());
    donacion.setObservaciones(request.observaciones());

    return DonacionResponse.from(donacionRepository.save(donacion));
  }

  public void eliminar(Long id) {
    Donacion donacion = buscarDonacion(id);
    acceso.propio(donacion.getDonador().getId());

    if (donacion.getEstado() != EstadoDonacion.DISPONIBLE) {
      throw new OperacionInvalidaException("No se puede eliminar una donacion entregada");
    }

    if (solicitudRepository.existsByDonacionId(id)) {
      donacion.setEstado(EstadoDonacion.CANCELADA);
      donacionRepository.save(donacion);
      return;
    }

    donacionRepository.delete(donacion);
  }

  Donacion buscarDonacion(Long id) {
    return donacionRepository
        .findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("Donacion no encontrada"));
  }
}
