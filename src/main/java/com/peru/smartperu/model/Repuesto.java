package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repuestos")
public class Repuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom_repuesto", nullable = false)
    private String nomRepuesto;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "num_modelo", nullable = false)
    private String numModelo;

    @Column(name = "cantidad", nullable = false)
    private String cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private String precioUnitario;
}
