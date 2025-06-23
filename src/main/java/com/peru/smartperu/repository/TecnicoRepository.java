package com.peru.smartperu.repository;

import com.peru.smartperu.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
    List<Tecnico> findByActivoTrue();
}