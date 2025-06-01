// src/main/java/com/peru/smartperu/dto/OrdenReparacionDto.java
package com.peru.smartperu.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Importa el enum del modelo para usarlo en el DTO
import static com.peru.smartperu.model.OrdenReparacion.EstadoOrden;


@Data
public class OrdenReparacionDto {
    private Integer idOrden;
    private Integer idDispositivo; // Para seleccionar el dispositivo
    private Integer idCliente;    // Para seleccionar el cliente (redundante pero útil para el formulario)
    private Integer idTecnico;    // Para seleccionar el técnico (puede ser nulo)

    private LocalDateTime fechaCreacion; // Puede que no necesites enviarlo desde el formulario
    private LocalDateTime fechaActualizacion; // Puede que no necesites enviarlo desde el formulario

    private String descripcionProblema;
    private String diagnosticoTecnico;
    private String solucionAplicada;
    private EstadoOrden estadoOrden; // Usamos el Enum directamente
    private BigDecimal costoEstimado;
    private BigDecimal costoFinal;
    private LocalDate fechaEntrega;
    private String observaciones;
}