// src/main/java/com/peru/smartperu/service/OrdenReparacionService.java
package com.peru.smartperu.service;

import com.peru.smartperu.model.OrdenReparacion;
import com.peru.smartperu.repository.OrdenReparacionRepository;
import com.peru.smartperu.dto.OrdenReparacionDto; // Necesario para
import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.model.Tecnico;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.peru.smartperu.model.OrdenReparacion.EstadoOrden; // Importa el Enum

@Service
@AllArgsConstructor
public class OrdenReparacionService {

    private final OrdenReparacionRepository ordenReparacionRepository;
    private final DispositivoService dispositivoService; // Para buscar el Dispositivo
    private final ClienteService clienteService;         // Para buscar el Cliente
    private final TecnicoService tecnicoService;         // Para buscar el Tecnico

    public List<OrdenReparacion> findAll() {
        return ordenReparacionRepository.findAll();
    }

    public OrdenReparacion findById(Integer id) {
        return ordenReparacionRepository.findById(id).orElse(null);
    }

    public OrdenReparacion save(OrdenReparacionDto ordenDto) {
        OrdenReparacion orden = new OrdenReparacion();

        // Si es una actualización, busca la orden existente
        if (ordenDto.getIdOrden() != null) {
            orden = ordenReparacionRepository.findById(ordenDto.getIdOrden())
                    .orElseThrow(() -> new RuntimeException("Orden de Reparación no encontrada con ID: " + ordenDto.getIdOrden()));
            // Las fechas de creación y actualización serán manejadas por @PreUpdate
        } else {
            // Si es una nueva orden, establece la fecha de creación
            orden.setFechaCreacion(LocalDateTime.now());
            orden.setFechaActualizacion(LocalDateTime.now());
            // Establece el estado inicial
            orden.setEstadoOrden(EstadoOrden.PENDIENTE_DIAGNOSTICO); // Estado por defecto
        }

        // Asignar Dispositivo
        if (ordenDto.getIdDispositivo() != null) {
            Dispositivo dispositivo = dispositivoService.findById(ordenDto.getIdDispositivo());
            if (dispositivo == null) {
                throw new RuntimeException("Dispositivo con ID " + ordenDto.getIdDispositivo() + " no encontrado.");
            }
            orden.setDispositivo(dispositivo);
        } else {
            throw new IllegalArgumentException("Debe seleccionar un dispositivo para la orden de reparación.");
        }

        // Asignar Cliente (opcionalmente puedes tomarlo del dispositivo si sabes que siempre coinciden)
        // Se mantiene para el formulario, como lo tienes en la tabla SQL
        if (ordenDto.getIdCliente() != null) {
            Cliente cliente = clienteService.findById(ordenDto.getIdCliente());
            if (cliente == null) {
                throw new RuntimeException("Cliente con ID " + ordenDto.getIdCliente() + " no encontrado.");
            }
            orden.setCliente(cliente);
        } else {
            throw new IllegalArgumentException("Debe seleccionar un cliente para la orden de reparación.");
        }

        // Asignar Técnico (puede ser nulo)
        if (ordenDto.getIdTecnico() != null) {
            Tecnico tecnico = tecnicoService.findById(ordenDto.getIdTecnico());
            if (tecnico == null) {
                throw new RuntimeException("Técnico con ID " + ordenDto.getIdTecnico() + " no encontrado.");
            }
            orden.setTecnico(tecnico);
            // Si asignas un técnico, podrías cambiar el estado a ASIGNADA o EN_DIAGNOSTICO
            if (orden.getEstadoOrden() == EstadoOrden.PENDIENTE_DIAGNOSTICO) {
                orden.setEstadoOrden(EstadoOrden.ASIGNADA);
            }
        } else {
            orden.setTecnico(null); // Asegura que el técnico sea null si no se selecciona
        }

        orden.setDescripcionProblema(ordenDto.getDescripcionProblema());
        orden.setDiagnosticoTecnico(ordenDto.getDiagnosticoTecnico());
        orden.setSolucionAplicada(ordenDto.getSolucionAplicada());

        // Solo actualiza el estado si viene en el DTO (útil para actualizaciones)
        if (ordenDto.getEstadoOrden() != null) {
            orden.setEstadoOrden(ordenDto.getEstadoOrden());
        }

        orden.setCostoEstimado(ordenDto.getCostoEstimado());
        orden.setCostoFinal(ordenDto.getCostoFinal());
        orden.setFechaEntrega(ordenDto.getFechaEntrega());
        orden.setObservaciones(ordenDto.getObservaciones());

        return ordenReparacionRepository.save(orden);
    }
}