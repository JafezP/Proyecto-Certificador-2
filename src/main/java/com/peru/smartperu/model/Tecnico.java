package com.peru.smartperu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tecnicos")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico")
    private Integer idTecnico;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @Column(name = "celular", nullable = false)
    private String celular;

    @Column(name = "correo_electronico", nullable = false, unique = true)
    private String correoElectronico;

    @Column(name = "especialidad", nullable = false)
    private String especialidad;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    // se agrego un nuevo campo
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    //le dice a JPA que esta entidad tiene una relacion con OrdenReparacion
    @OneToMany(mappedBy = "tecnico")
    private List<OrdenReparacion> ordenes;
}