package com.peru.smartperu.service;

import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.repository.DispositvoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DispositivoService {

    private final DispositvoRepository dispositvoRepository;

    public List<Dispositivo> findAll() {
        return dispositvoRepository.findAll();
    }

    public Dispositivo findById(Integer id) {
        return dispositvoRepository.findById(id).orElse(null);
    }

    public void save(Dispositivo dispositivo) {
        dispositvoRepository.save(dispositivo);
    }

    public boolean existsByNumeroSerieImei(String imei) {
        return dispositvoRepository.existsByNumeroSerieImei(imei);
    }

    // Nuevos métodos de búsqueda
    public List<Dispositivo> searchByImei(String imei) {
        return dispositvoRepository.findByNumeroSerieImeiContainingIgnoreCase(imei);
    }

    public List<Dispositivo> searchByClienteNombre(String nombreCliente) {
        return dispositvoRepository.findByClienteNombreCompletoContainingIgnoreCase(nombreCliente);
    }

    public List<Dispositivo> searchByTipoDispositivo(String tipo) {
        return dispositvoRepository.findByTipoDispositivoContainingIgnoreCase(tipo);
    }

    // Método para búsqueda general por palabra clave (si decides usarlo en el controlador)
    public List<Dispositivo> searchDispositivos(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return dispositvoRepository.findAll(); // Si no hay texto de búsqueda, mostrar todos
        }
        return dispositvoRepository.searchDispositivosByKeyword(searchText);
    }

    // Método para búsqueda avanzada con múltiples campos (si decides usarlo en el controlador)
    public List<Dispositivo> searchAdvanced(String imei, String cliente, String tipo) {
        // Lógica para construir la consulta basada en los campos no nulos/vacíos
        // Esto puede volverse complejo, la @Query `searchDispositivosByKeyword` o
        // la combinación de `findBy...And...` son más simples para empezar.
        // Por simplicidad, se puede adaptar `searchDispositivosByKeyword` o usar una combinación de los métodos anteriores
        // o construir una query dinámica con Specification/Criteria API si la complejidad aumenta.

        // Para esta HU, usaremos el `searchDispositivosByKeyword` para una única barra de búsqueda
        // o los métodos individuales si la UI tiene campos separados.
        // A continuación, se implementa una búsqueda simple que combina los campos en el controlador.
        return null; // Este método no será usado directamente con la implementación simple de la UI.
    }
}