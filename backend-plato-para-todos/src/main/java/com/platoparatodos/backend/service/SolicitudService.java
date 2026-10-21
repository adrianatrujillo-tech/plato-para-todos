package com.platoparatodos.backend.service;

import com.platoparatodos.backend.dto.SolicitudRequest;
import com.platoparatodos.backend.dto.SolicitudResponse;
import com.platoparatodos.backend.dto.SolicitudUpdateRequest;
import com.platoparatodos.backend.exception.OperacionInvalidaException;
import com.platoparatodos.backend.exception.RecursoNoEncontradoException;
import com.platoparatodos.backend.model.Donacion;
import com.platoparatodos.backend.model.EstadoDonacion;
import com.platoparatodos.backend.model.EstadoSolicitud;
import com.platoparatodos.backend.model.Rol;
import com.platoparatodos.backend.model.Solicitud;
import com.platoparatodos.backend.model.Usuario;
import com.platoparatodos.backend.repository.DonacionRepository;
import com.platoparatodos.backend.repository.SolicitudRepository;
import com.platoparatodos.backend.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {
  @org.springframework.beans.factory.annotation.Autowired private AccesoService acceso;

  private final SolicitudRepository solicitudRepository;
  private final DonacionService donacionService;
  private final DonacionRepository donacionRepository;
  private final UsuarioRepository usuarioRepository;

  public SolicitudService(
      SolicitudRepository solicitudRepository,
      DonacionService donacionService,
      DonacionRepository donacionRepository,
      UsuarioRepository usuarioRepository) {
    this.solicitudRepository = solicitudRepository;
    this.donacionService = donacionService;
    this.donacionRepository = donacionRepository;
    this.usuarioRepository = usuarioRepository;
  }

  public List<SolicitudResponse> listarTodas() {
    var u = acceso.actual();
    if (u.getRol() == Rol.DONADOR) return listarPorDonador(u.getId());
    if (u.getRol() == Rol.BENEFICIARIO) return listarPorBeneficiario(u.getId());
    return solicitudRepository.findAll().stream().map(SolicitudResponse::from).toList();
  }

  public List<SolicitudResponse> listarPorBeneficiario(Long beneficiarioId) {
    acceso.propio(beneficiarioId);
    return solicitudRepository.findByBeneficiarioId(beneficiarioId).stream()
        .map(SolicitudResponse::from)
        .toList();
  }

  public List<SolicitudResponse> listarPorDonador(Long donadorId) {
    acceso.propio(donadorId);
    return solicitudRepository.findByDonacionDonadorId(donadorId).stream()
        .map(SolicitudResponse::from)
        .toList();
  }

  public SolicitudResponse obtenerPorId(Long id) {
    var s = buscarSolicitud(id);
    acceso.solicitud(s);
    return SolicitudResponse.from(s);
  }

  public SolicitudResponse crear(SolicitudRequest request) {
    acceso.rol(Rol.BENEFICIARIO);
    acceso.propio(request.beneficiarioId());
    Donacion donacion = donacionService.buscarDonacion(request.donacionId());
    Usuario beneficiario =
        usuarioRepository
            .findById(request.beneficiarioId())
            .orElseThrow(() -> new RecursoNoEncontradoException("Beneficiario no encontrado"));

    if (beneficiario.getRol() != Rol.BENEFICIARIO) {
      throw new OperacionInvalidaException("El usuario indicado no tiene rol BENEFICIARIO");
    }

    if (donacion.getEstado() != EstadoDonacion.DISPONIBLE) {
      throw new OperacionInvalidaException("La donacion no esta disponible");
    }

    if (request.cantidadSolicitada() > donacion.getCantidad()) {
      throw new OperacionInvalidaException("La cantidad solicitada supera el stock disponible");
    }

    Solicitud solicitud = new Solicitud();
    solicitud.setDonacion(donacion);
    solicitud.setBeneficiario(beneficiario);
    solicitud.setCantidadSolicitada(request.cantidadSolicitada());
    solicitud.setFechaPropuestaRecojo(request.fechaPropuestaRecojo());
    solicitud.setMotivo(request.motivo());

    return SolicitudResponse.from(solicitudRepository.save(solicitud));
  }

  @org.springframework.transaction.annotation.Transactional
  public SolicitudResponse cambiarEstado(Long id, EstadoSolicitud estado) {
    Solicitud solicitud = buscarSolicitud(id);
    acceso.propio(solicitud.getDonacion().getDonador().getId());
    var donacionBloqueada =
        donacionRepository.bloquear(solicitud.getDonacion().getId()).orElseThrow();
    var previo = solicitud.getEstado();
    if (previo == estado) return SolicitudResponse.from(solicitud);
    if (!((previo == EstadoSolicitud.PENDIENTE
            && (estado == EstadoSolicitud.APROBADA
                || estado == EstadoSolicitud.RECHAZADA
                || estado == EstadoSolicitud.CANCELADA))
        || (previo == EstadoSolicitud.APROBADA
            && (estado == EstadoSolicitud.ENTREGADA || estado == EstadoSolicitud.CANCELADA))))
      throw new OperacionInvalidaException("Cambio de estado no permitido");
    if (estado == EstadoSolicitud.APROBADA
        && donacionBloqueada.getEstado() != EstadoDonacion.DISPONIBLE)
      throw new OperacionInvalidaException("La donación ya está reservada");
    if (estado == EstadoSolicitud.CANCELADA && previo == EstadoSolicitud.APROBADA) {
      donacionBloqueada.setEstado(EstadoDonacion.DISPONIBLE);
      donacionRepository.save(donacionBloqueada);
    }

    solicitud.setEstado(estado);

    if (estado == EstadoSolicitud.APROBADA) {
      Donacion donacion = solicitud.getDonacion();
      donacion.setEstado(EstadoDonacion.RESERVADA);
      donacionRepository.save(donacion);
    }

    if (estado == EstadoSolicitud.ENTREGADA) {
      Donacion donacion = solicitud.getDonacion();
      donacion.setEstado(EstadoDonacion.ENTREGADA);
      donacionRepository.save(donacion);
    }

    return SolicitudResponse.from(solicitudRepository.save(solicitud));
  }

  public SolicitudResponse editar(Long id, SolicitudUpdateRequest request) {
    Solicitud solicitud = buscarSolicitud(id);
    acceso.propio(solicitud.getBeneficiario().getId());

    if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
      throw new OperacionInvalidaException("Solo se pueden editar solicitudes pendientes");
    }

    if (request.cantidadSolicitada() > solicitud.getDonacion().getCantidad()) {
      throw new OperacionInvalidaException("La cantidad solicitada supera el stock disponible");
    }

    solicitud.setCantidadSolicitada(request.cantidadSolicitada());
    solicitud.setFechaPropuestaRecojo(request.fechaPropuestaRecojo());
    solicitud.setMotivo(request.motivo());

    return SolicitudResponse.from(solicitudRepository.save(solicitud));
  }

  public void eliminar(Long id) {
    Solicitud solicitud = buscarSolicitud(id);
    acceso.propio(solicitud.getBeneficiario().getId());

    if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
      throw new OperacionInvalidaException("Solo se pueden eliminar solicitudes pendientes");
    }

    solicitudRepository.delete(solicitud);
  }

  private Solicitud buscarSolicitud(Long id) {
    return solicitudRepository
        .findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada"));
  }
}
