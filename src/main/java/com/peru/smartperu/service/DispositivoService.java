package com.peru.smartperu.service;

import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.repository.DispositvoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DispositivoService {

    private final DispositvoRepository dispositvoRepository;

    @Transactional(readOnly = true)
    public List<Dispositivo> findAll() {
        return dispositvoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Dispositivo findById(Integer id) {
        return dispositvoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Dispositivo save(Dispositivo dispositivo) {
        return dispositvoRepository.save(dispositivo);
    }

    @Transactional(readOnly = true)
    public boolean existsByNumeroSerieImei(String imei) {
        return dispositvoRepository.existsByNumeroSerieImei(imei);
    }

    @Transactional(readOnly = true)
    public List<Dispositivo> searchByImei(String imei) {
        return dispositvoRepository.findByNumeroSerieImeiContainingIgnoreCase(imei);
    }

    @Transactional(readOnly = true)
    public List<Dispositivo> searchByClienteNombre(String nombreCliente) {
        return dispositvoRepository.findByClienteNombreCompletoContainingIgnoreCase(nombreCliente);
    }

    @Transactional(readOnly = true)
    public List<Dispositivo> searchByTipoDispositivo(String tipo) {
        return dispositvoRepository.findByTipoDispositivoContainingIgnoreCase(tipo);
    }

    // Método para la HU04: Actualizar un dispositivo
    @Transactional
    public Dispositivo updateDispositivo(Integer id, Dispositivo updatedDispositivo) {
        Optional<Dispositivo> existingDispositivoOpt = dispositvoRepository.findById(id);
        if (existingDispositivoOpt.isEmpty()) {
            return null; // O lanzar una excepción si el dispositivo no existe
        }

        Dispositivo existingDispositivo = existingDispositivoOpt.get();

        // Actualizar los campos que se permiten modificar en la interfaz de usuario
        existingDispositivo.setMarca(updatedDispositivo.getMarca());
        existingDispositivo.setTipoDispositivo(updatedDispositivo.getTipoDispositivo());
        existingDispositivo.setNumeroSerieImei(updatedDispositivo.getNumeroSerieImei());
        existingDispositivo.setModelo(updatedDispositivo.getModelo());
        existingDispositivo.setColor(updatedDispositivo.getColor());
        existingDispositivo.setObservacionesAdicionales(updatedDispositivo.getObservacionesAdicionales());
        existingDispositivo.setDescripcionProblemaInicial(updatedDispositivo.getDescripcionProblemaInicial());


        return dispositvoRepository.save(existingDispositivo);
    }
}