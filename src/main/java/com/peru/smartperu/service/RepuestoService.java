package com.peru.smartperu.service;

import com.peru.smartperu.model.Repuesto;
import com.peru.smartperu.repository.RepuestoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RepuestoService {

    private final RepuestoRepository repuestoRepository;

    public List<Repuesto> findAll() {
        return repuestoRepository.findAll();
    }

    public Repuesto findById(Integer id) {
        return repuestoRepository.findById(id).orElse(null);
    }

    public void save(Repuesto repuesto) {
        repuestoRepository.save(repuesto);
    }
}
