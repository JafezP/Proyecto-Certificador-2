package com.peru.smartperu.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

public class DispositivoDto {
    private Integer id_dispositivo;
    //private Integer id_cliente;
    private String tipo_dispositivo;
    private String marca;
    private String modelo;
    private String numero_serie_imei;
    private String color;
    private String descripcion_problema_inicial;
    private String observaciones_adicionales;
    private Date fecha_registro;
}
