package com.peru.smartperu.repository;

import com.peru.smartperu.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Query("SELECT c FROM Cliente c WHERE " +
            "LOWER(c.nombreCompleto) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.dni) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.celular) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.correoElectronico) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Cliente> searchByKeyword(@Param("keyword") String keyword);
}
