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
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "celular", nullable = false)
    private String celular;

    @Column(name = "correo_electronico", nullable = false)
    private String correoElectronico;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;
}
