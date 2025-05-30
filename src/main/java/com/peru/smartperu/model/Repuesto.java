package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repuestos")
public class Repuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repuesto")
    private Integer idRepuesto;

    @Column(name = "nombre_repuesto", nullable = false)
    private String nombreRepuesto;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "numero_parte", nullable = false)
    private String numeroParte;

    @Column(name = "cantidad_stock", nullable = false)
    private String cantidadStock;

    @Column(name = "precio_compra", nullable = false)
    private String precioCompra;

    @Column(name = "precio_venta_sugerido", nullable = false)
    private String precioVentaSugerido;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;
}
