package com.platoparatodos.backend.repository;

import com.platoparatodos.backend.model.Donacion;
import com.platoparatodos.backend.model.EstadoDonacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonacionRepository extends JpaRepository<Donacion, Long> {
  @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
  @org.springframework.data.jpa.repository.Query("select d from Donacion d where d.id=:id")
  java.util.Optional<Donacion> bloquear(Long id);

  List<Donacion> findByEstado(EstadoDonacion estado);

  List<Donacion> findByDonadorId(Long donadorId);
}
