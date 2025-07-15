package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orden_venta")
public class OrdenVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    @Column(name = "impuesto", nullable = false)
    private Double impuesto;

    @Column(name = "total", nullable = false)
    private Double total;

    @OneToMany(mappedBy = "ordenVenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOrdenVenta> items;
}
