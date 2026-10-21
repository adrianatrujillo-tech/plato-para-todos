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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final DonacionService donacionService;
    private final DonacionRepository donacionRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitudService(
            SolicitudRepository solicitudRepository,
            DonacionService donacionService,
            DonacionRepository donacionRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.solicitudRepository = solicitudRepository;
        this.donacionService = donacionService;
        this.donacionRepository = donacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<SolicitudResponse> listarTodas() {
        return solicitudRepository.findAll().stream()
                .map(SolicitudResponse::from)
                .toList();
    }

    public List<SolicitudResponse> listarPorBeneficiario(Long beneficiarioId) {
        return solicitudRepository.findByBeneficiarioId(beneficiarioId).stream()
                .map(SolicitudResponse::from)
                .toList();
    }

    public List<SolicitudResponse> listarPorDonador(Long donadorId) {
        return solicitudRepository.findByDonacionDonadorId(donadorId).stream()
                .map(SolicitudResponse::from)
                .toList();
    }

    public SolicitudResponse obtenerPorId(Long id) {
        return SolicitudResponse.from(buscarSolicitud(id));
    }

    public SolicitudResponse crear(SolicitudRequest request) {
        Donacion donacion = donacionService.buscarDonacion(request.donacionId());
        Usuario beneficiario = usuarioRepository.findById(request.beneficiarioId())
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

    public SolicitudResponse cambiarEstado(Long id, EstadoSolicitud estado) {
        Solicitud solicitud = buscarSolicitud(id);

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

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new OperacionInvalidaException("Solo se pueden eliminar solicitudes pendientes");
        }

        solicitudRepository.delete(solicitud);
    }

    private Solicitud buscarSolicitud(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada"));
    }
}
