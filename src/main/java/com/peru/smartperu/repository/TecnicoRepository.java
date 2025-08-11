package com.peru.smartperu.repository;

import com.peru.smartperu.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
    List<Tecnico> findByActivoTrue();

    // Nuevo método para buscar tecnicos por una palabra clave
    @Query("SELECT t FROM Tecnico t WHERE t.activo = true AND " +
            "(LOWER(t.nombreCompleto) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.dni) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.especialidad) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.celular) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.correoElectronico) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Tecnico> searchByKeyword(@Param("keyword") String keyword);
}