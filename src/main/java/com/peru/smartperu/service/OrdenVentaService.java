package com.peru.smartperu.service;

import com.peru.smartperu.model.OrdenVenta;
import com.peru.smartperu.repository.OrdenVentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrdenVentaService {

    private final OrdenVentaRepository ordenVentaRepository;

    public List<OrdenVenta> findAll() {
        return ordenVentaRepository.findAll();
    }

    public void save(OrdenVenta ordenVenta) {
        ordenVentaRepository.save(ordenVenta);
    }

    public OrdenVenta findById(Integer id) {
        return ordenVentaRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        ordenVentaRepository.deleteById(id);
    }
}

