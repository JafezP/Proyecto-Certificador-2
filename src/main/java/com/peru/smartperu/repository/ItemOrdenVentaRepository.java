package com.peru.smartperu.repository;

import com.peru.smartperu.model.ItemOrdenVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrdenVentaRepository extends JpaRepository<ItemOrdenVenta, Integer> {
}
