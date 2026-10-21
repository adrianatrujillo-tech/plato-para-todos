package com.platoparatodos.backend.config;

import com.platoparatodos.backend.model.Donacion;
import com.platoparatodos.backend.model.ModalidadEntrega;
import com.platoparatodos.backend.model.Rol;
import com.platoparatodos.backend.model.Solicitud;
import com.platoparatodos.backend.model.Usuario;
import com.platoparatodos.backend.repository.DonacionRepository;
import com.platoparatodos.backend.repository.SolicitudRepository;
import com.platoparatodos.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final DonacionRepository donacionRepository;
    private final SolicitudRepository solicitudRepository;

    public DataInitializer(
            UsuarioRepository usuarioRepository,
            DonacionRepository donacionRepository,
            SolicitudRepository solicitudRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.donacionRepository = donacionRepository;
        this.solicitudRepository = solicitudRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return;
        }

        usuarioRepository.save(
                new Usuario("Administrador", "admin@test.com", "123456", Rol.ADMIN, "Plato Para Todos")
        );

        Usuario donador = usuarioRepository.save(
                new Usuario("Lizbeth", "donador@test.com", "123456", Rol.DONADOR, "Supermercados Peru")
        );
        donador.setTelefono("999888777");
        donador.setDireccion("Av. Los Olivos 123");
        usuarioRepository.save(donador);

        Usuario beneficiario = usuarioRepository.save(
                new Usuario("Beneficiario", "beneficiario@test.com", "123456", Rol.BENEFICIARIO, "Comedor Esperanza")
        );
        beneficiario.setTelefono("988777666");
        beneficiario.setDireccion("Jr. Solidaridad 456");
        usuarioRepository.save(beneficiario);

        Donacion frutas = crearDonacion(
                "Frutas Variadas",
                "Frutas",
                50,
                "Kg",
                LocalDate.now().plusDays(15),
                ModalidadEntrega.RETIRO,
                "Av. Los Olivos 123 - Los Olivos",
                "Lunes a Viernes 08:00 AM - 05:00 PM",
                "Frutas frescas aptas para consumo inmediato.",
                "Se recomienda recoger el mismo dia.",
                donador
        );

        crearDonacion(
                "Verduras Frescas",
                "Verduras",
                80,
                "Kg",
                LocalDate.now().plusDays(12),
                ModalidadEntrega.ENTREGA_COORDINADA,
                "Mercado Central - SMP",
                "Lunes a Sabado 07:00 AM - 04:00 PM",
                "Verduras frescas en excelente estado.",
                "Coordinar entrega con 24 horas de anticipacion.",
                donador
        );

        Solicitud solicitud = new Solicitud();
        solicitud.setDonacion(frutas);
        solicitud.setBeneficiario(beneficiario);
        solicitud.setCantidadSolicitada(20);
        solicitud.setFechaPropuestaRecojo(LocalDate.now().plusDays(2));
        solicitud.setMotivo("Preparacion de almuerzos comunitarios.");
        solicitudRepository.save(solicitud);
    }

    private Donacion crearDonacion(
            String titulo,
            String categoria,
            Integer cantidad,
            String unidad,
            LocalDate fechaVencimiento,
            ModalidadEntrega modalidadEntrega,
            String direccionRecojo,
            String horarioDisponible,
            String descripcion,
            String observaciones,
            Usuario donador
    ) {
        Donacion donacion = new Donacion();
        donacion.setTitulo(titulo);
        donacion.setCategoria(categoria);
        donacion.setCantidad(cantidad);
        donacion.setUnidad(unidad);
        donacion.setFechaVencimiento(fechaVencimiento);
        donacion.setModalidadEntrega(modalidadEntrega);
        donacion.setDireccionRecojo(direccionRecojo);
        donacion.setHorarioDisponible(horarioDisponible);
        donacion.setDescripcion(descripcion);
        donacion.setObservaciones(observaciones);
        donacion.setDonador(donador);

        return donacionRepository.save(donacion);
    }
}
