package com.peru.smartperu.dto;

import lombok.Data;

import java.time.LocalDate; // AGREGA ESTE IMPORT

@Data
public class DispositivoDto {
    private Integer idDispositivo;
    private Integer idCliente;
    private String tipoDispositivo;
    private String marca;
    private String modelo;
    private String numeroSerieImei;
    private String color;
    private String descripcionProblemaInicial;
    private String observacionesAdicionales;
    private LocalDate fechaRegistro; // CAMBIO: de Date a LocalDate
}