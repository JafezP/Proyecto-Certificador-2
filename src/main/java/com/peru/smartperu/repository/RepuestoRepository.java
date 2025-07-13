package com.peru.smartperu.repository;

import com.peru.smartperu.model.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List; // Importar List

public interface RepuestoRepository extends JpaRepository<Repuesto, Integer> {

    // Nuevo método para buscar repuestos por nombre, descripción o número de parte
    @Query("SELECT r FROM Repuesto r WHERE " +
            "LOWER(r.nombreRepuesto) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(r.descripcion) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(r.numeroParte) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Repuesto> searchByNombreDescripcionNumeroParte(@Param("searchText") String searchText);
}