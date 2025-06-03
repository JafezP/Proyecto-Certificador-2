package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date; // java.util.Date está bien, pero java.time.LocalDate/LocalDateTime es más moderno

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dispositivos")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispositivo") // Es buena práctica especificar el nombre de la columna para la PK también
    private Integer idDispositivo; // CAMBIO: de id_dispositivo a idDispositivo


    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "tipo_dispositivo", nullable = false)
    private String tipoDispositivo; // ¡YA ESTABA BIEN!

    @Column(name = "marca", nullable = false)
    private String marca; // ¡YA ESTABA BIEN!

    @Column(name = "modelo", nullable = false)
    private String modelo; // ¡YA ESTABA BIEN!

    @Column(name = "numero_serie_imei", nullable = false)
    private String numeroSerieImei; // CAMBIO CLAVE: de numeroSerie_imei a numeroSerieImei (sin guion bajo)

    @Column(name = "color", nullable = false)
    private String color; // ¡YA ESTABA BIEN!

    @Column(name = "descripcion_problema_inicial", nullable = false)
    private String descripcionProblemaInicial; // ¡YA ESTABA BIEN!

    @Column(name = "observaciones_adicionales")
    private String observacionesAdicionales; // ¡YA ESTABA BIEN!

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro; // ¡YA ESTABA BIEN!

}