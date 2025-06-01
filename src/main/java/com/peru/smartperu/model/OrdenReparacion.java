// src/main/java/com/peru/smartperu/model/OrdenReparacion.java
package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordenes_reparacion") // Coincide con el nombre de tu tabla SQL
public class OrdenReparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;

    @ManyToOne
    @JoinColumn(name = "id_dispositivo", nullable = false)
    private Dispositivo dispositivo;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_tecnico") // Puede ser nulo
    private Tecnico tecnico;

    @Column(name = "fecha_creacion", nullable = false, updatable = false) // updatable=false para que no se cambie en updates
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "descripcion_problema", nullable = false, columnDefinition = "TEXT")
    private String descripcionProblema;

    @Column(name = "diagnostico_tecnico", columnDefinition = "TEXT")
    private String diagnosticoTecnico;

    @Column(name = "solucion_aplicada", columnDefinition = "TEXT")
    private String solucionAplicada;

    @Enumerated(EnumType.STRING) // Guarda el ENUM como String en la BD
    @Column(name = "estado_orden", nullable = false)
    private EstadoOrden estadoOrden;

    @Column(name = "costo_estimado", precision = 10, scale = 2)
    private BigDecimal costoEstimado;

    @Column(name = "costo_final", precision = 10, scale = 2)
    private BigDecimal costoFinal;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    // Enum para el estado de la orden
    public enum EstadoOrden {
        PENDIENTE_DIAGNOSTICO("Pendiente de diagnóstico"),
        ASIGNADA("Asignada"),
        EN_DIAGNOSTICO("En diagnóstico"),
        EN_REPARACION("En reparación"),
        ESPERANDO_REPUESTO("Esperando repuesto"),
        REPARADO("Reparado"),
        LISTA_PARA_ENTREGA("Lista para entrega"),
        ENTREGADO("Entregado"),
        CANCELADA("Cancelada");

        private final String displayValue;

        EstadoOrden(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    // Métodos para manejar la fecha de creación y actualización (opcional si lo manejas en la DB)
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (estadoOrden == null) { // Asegura un estado por defecto si no se establece
            estadoOrden = EstadoOrden.PENDIENTE_DIAGNOSTICO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}