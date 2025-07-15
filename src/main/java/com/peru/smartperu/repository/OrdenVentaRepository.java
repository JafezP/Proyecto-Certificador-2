package com.peru.smartperu.repository;

import com.peru.smartperu.model.OrdenVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenVentaRepository extends JpaRepository<OrdenVenta, Integer> {
}
