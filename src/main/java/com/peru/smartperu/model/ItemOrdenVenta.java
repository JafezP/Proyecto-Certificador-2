package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_orden_venta")
public class ItemOrdenVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orden_venta_id")
    private OrdenVenta ordenVenta;

    @Column(name = "tipo_item") // "smartphone", "accesorio", "servicio"
    private String tipoItem;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "descripcion_id")
    private Integer descripcionId;
}
