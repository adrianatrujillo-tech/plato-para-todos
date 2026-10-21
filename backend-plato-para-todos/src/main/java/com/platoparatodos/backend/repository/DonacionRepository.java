package com.platoparatodos.backend.repository;

import com.platoparatodos.backend.model.Donacion;
import com.platoparatodos.backend.model.EstadoDonacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {

    List<Donacion> findByEstado(EstadoDonacion estado);

    List<Donacion> findByDonadorId(Long donadorId);
}
