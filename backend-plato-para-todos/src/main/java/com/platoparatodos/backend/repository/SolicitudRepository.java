package com.platoparatodos.backend.repository;

import com.platoparatodos.backend.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByBeneficiarioId(Long beneficiarioId);

    List<Solicitud> findByDonacionDonadorId(Long donadorId);

    boolean existsByDonacionId(Long donacionId);
}
