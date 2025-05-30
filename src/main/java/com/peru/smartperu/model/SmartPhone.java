package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "smartphones")
public class SmartPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_smart_phone")
    private Integer idSmartPhone;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "imei", nullable = false)
    private String imei;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "almacenamiento", nullable = false)
    private String almacenamiento;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;
}
