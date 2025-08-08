package com.peru.smartperu.repository;

import com.peru.smartperu.model.SmartPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmartPhoneRepository extends JpaRepository<SmartPhone, Integer> {
    List<SmartPhone> findByDisponibleTrue();

    // Nuevo método para buscar por marca o modelo, ignorando mayúsculas y minúsculas
    @Query("SELECT s FROM SmartPhone s WHERE LOWER(s.marca) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.modelo) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<SmartPhone> searchByMarcaOrModelo(@Param("keyword") String keyword);
}