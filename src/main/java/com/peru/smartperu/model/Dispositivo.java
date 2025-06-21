package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List; // <<< AÑADE ESTE IMPORT

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dispositivos")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispositivo")
    private Integer idDispositivo;

    @ManyToOne(fetch = FetchType.LAZY) // Se añade FetchType.LAZY para una mejor performance.
    @JoinColumn(name = "id_cliente") // Asegúrate de que esta columna sea nullable=false si siempre debe tener un cliente
    private Cliente cliente;

    @Column(name = "tipo_dispositivo", nullable = false)
    private String tipoDispositivo;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "numero_serie_imei", nullable = false)
    private String numeroSerieImei;

    @Column(name = "color", nullable = false) // Según tu código original, este es nullable=false
    private String color;

    @Column(name = "descripcion_problema_inicial", nullable = false)
    private String descripcionProblemaInicial;

    @Column(name = "observaciones_adicionales")
    private String observacionesAdicionales;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    // --- CAMBIO PARA LA HU05: RELACIÓN CON ORDENES DE REPARACIÓN ---
    @OneToMany(mappedBy = "dispositivo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrdenReparacion> ordenesReparacion;
    // 'mappedBy' indica que la relación es bidireccional y el campo 'dispositivo' en OrdenReparacion es el dueño.
    // 'fetch = FetchType.LAZY' significa que las órdenes no se cargarán hasta que se acceda a ellas.
    // 'cascade = CascadeType.ALL' significa que si se elimina un Dispositivo, sus OrdenReparacion también se eliminarán.
}