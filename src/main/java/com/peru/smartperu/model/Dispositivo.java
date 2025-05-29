package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

import javax.xml.crypto.Data;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dispositivos")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_dispositivo;
    //@ManyToOne
    //@JoinColumn(name = "id_cliente")
    //private Integer id_cliente;
    @Column(name = "tipo_dispositivo", nullable = false)
    private String tipo_dispositivo;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "numero_serie_imei", nullable = false)
    private String numero_serie_imei;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "descripcion_problema_inicial", nullable = false)
    private String descripcion_problema_inicial;

    @Column(name = "observaciones_adicionales")
    private String observaciones_adicionales;

    @Column(name = "fecha_registro", nullable = false)
    private Date fecha_registro;




}
