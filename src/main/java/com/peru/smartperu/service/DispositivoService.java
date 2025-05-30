package com.peru.smartperu.service;

import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.repository.DispositvoRepository; // <-- Importa el repositorio
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // Para manejar el resultado de findById

@Service
@AllArgsConstructor
public class DispositivoService {
    // CAMBIO CLAVE: Inyecta el repositorio, no el propio servicio
    private final DispositvoRepository dispositvoRepository;

    public List<Dispositivo> findAll() {
        return dispositvoRepository.findAll(); // Llama al repositorio
    }

    public Dispositivo findById(Integer id) {
        // Maneja el Optional que retorna JpaRepository
        return dispositvoRepository.findById(id).orElse(null); // Retorna null si no lo encuentra
    }

    public Dispositivo save(Dispositivo dispositivo) {
        return dispositvoRepository.save(dispositivo); // Llama al repositorio
    }
}