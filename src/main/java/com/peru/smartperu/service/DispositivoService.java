package com.peru.smartperu.service;

import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.repository.DispositivoRepository; // <<< CORREGIDO EL NOMBRE
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate; // Asegúrate de que este import esté presente
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository; // <<< CORREGIDO EL NOMBRE

    @Transactional(readOnly = true)
    public List<Dispositivo> findAll() {
        return dispositivoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Dispositivo findById(Integer id) {
        // Al cargar el dispositivo para detalles, las órdenes de reparación se cargarán lazy
        return dispositivoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Dispositivo save(Dispositivo dispositivo) {
        // Asegúrate de establecer la fecha de registro si es nula
        if (dispositivo.getFechaRegistro() == null) {
            dispositivo.setFechaRegistro(LocalDate.now());
        }
        return dispositivoRepository.save(dispositivo);
    }

    @Transactional(readOnly = true)
    public boolean existsByNumeroSerieImei(String imei) {
        return dispositivoRepository.existsByNumeroSerieImei(imei);
    }

    @Transactional(readOnly = true)
    public List<Dispositivo> searchByImei(String imei) {
        return dispositivoRepository.findByNumeroSerieImeiContainingIgnoreCase(imei);
    }

    @Transactional(readOnly = true)
    public List<Dispositivo> searchByClienteNombre(String nombreCliente) {
        return dispositivoRepository.findByClienteNombreCompletoContainingIgnoreCase(nombreCliente);
    }

    @Transactional(readOnly = true)
    public List<Dispositivo> searchByTipoDispositivo(String tipo) {
        return dispositivoRepository.findByTipoDispositivoContainingIgnoreCase(tipo);
    }

    // Método para la HU04: Actualizar un dispositivo
    @Transactional
    public Dispositivo updateDispositivo(Integer id, Dispositivo updatedDispositivo) {
        Optional<Dispositivo> existingDispositivoOpt = dispositivoRepository.findById(id);
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



        return dispositivoRepository.save(existingDispositivo);
    }
}