// src/main/java/com/peru/smartperu/repository/OrdenReparacionRepository.java
package com.peru.smartperu.repository;

import com.peru.smartperu.model.OrdenReparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenReparacionRepository extends JpaRepository<OrdenReparacion, Integer> {
}