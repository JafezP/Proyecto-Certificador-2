package com.peru.smartperu.service;

import com.peru.smartperu.model.Repuesto;
import com.peru.smartperu.repository.RepuestoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional

import java.util.List;

@Service
@AllArgsConstructor
public class RepuestoService {

    private final RepuestoRepository repuestoRepository;

    @Transactional(readOnly = true) // Añadir @Transactional(readOnly = true) para métodos de lectura
    public List<Repuesto> findAll() {
        return repuestoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Repuesto findById(Integer id) {
        return repuestoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Repuesto repuesto) {
        repuestoRepository.save(repuesto);
    }

    @Transactional
    public void deleteById(Integer id) {
        repuestoRepository.deleteById(id);
    }

    // Nuevo método de servicio para buscar
    @Transactional(readOnly = true)
    public List<Repuesto> searchRepuestos(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return findAll(); // Si el texto de búsqueda está vacío, devuelve todos los repuestos
        }
        return repuestoRepository.searchByNombreDescripcionNumeroParte(searchText);
    }
}