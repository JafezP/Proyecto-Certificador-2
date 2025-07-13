package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Comparator; // Importar Comparator
import java.util.List;
import java.util.Optional; // Importar Optional

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

    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY para evitar carga innecesaria si no se necesita el cliente
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "tipo_dispositivo", length = 50, nullable = false)
    private String tipoDispositivo;

    @Column(name = "marca", length = 50, nullable = false)
    private String marca;

    @Column(name = "modelo", length = 50, nullable = false)
    private String modelo;

    @Column(name = "numero_serie_imei", length = 100, unique = true, nullable = false)
    private String numeroSerieImei;

    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "descripcion_problema_inicial", columnDefinition = "TEXT")
    private String descripcionProblemaInicial;

    @Column(name = "observaciones_adicionales", columnDefinition = "TEXT")
    private String observacionesAdicionales;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdenReparacion> ordenesReparacion;

    // --- NUEVO MÉTODO HELPER ---
    public Optional<OrdenReparacion> getLastOrdenReparacion() {
        if (this.ordenesReparacion == null || this.ordenesReparacion.isEmpty()) {
            return Optional.empty();
        }
        // Ordenar por fechaCreacion de forma descendente y tomar el primero
        return this.ordenesReparacion.stream()
                .max(Comparator.comparing(OrdenReparacion::getFechaCreacion));
    }
}