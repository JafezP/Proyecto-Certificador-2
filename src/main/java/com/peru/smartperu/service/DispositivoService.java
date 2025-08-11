package com.peru.smartperu.service;

import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.model.OrdenReparacion; // AÑADIDO: Importar para acceder al enum EstadoOrden
import com.peru.smartperu.repository.DispositivoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;

    @Transactional(readOnly = true)
    public List<Dispositivo> findAll() {
        return dispositivoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Dispositivo findById(Integer id) {
        return dispositivoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Dispositivo save(Dispositivo dispositivo) {
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

    @Transactional
    public Dispositivo updateDispositivo(Integer id, Dispositivo updatedDispositivo) {
        Optional<Dispositivo> existingDispositivoOpt = dispositivoRepository.findById(id);
        if (existingDispositivoOpt.isEmpty()) {
            return null;
        }

        Dispositivo existingDispositivo = existingDispositivoOpt.get();

        existingDispositivo.setMarca(updatedDispositivo.getMarca());
        existingDispositivo.setTipoDispositivo(updatedDispositivo.getTipoDispositivo());
        existingDispositivo.setNumeroSerieImei(updatedDispositivo.getNumeroSerieImei());
        existingDispositivo.setModelo(updatedDispositivo.getModelo());
        existingDispositivo.setColor(updatedDispositivo.getColor());
        existingDispositivo.setObservacionesAdicionales(updatedDispositivo.getObservacionesAdicionales());
        existingDispositivo.setDescripcionProblemaInicial(updatedDispositivo.getDescripcionProblemaInicial());

        return dispositivoRepository.save(existingDispositivo);
    }


    // --- NUEVO MÉTODO PARA ELIMINAR ---
    @Transactional
    public boolean deleteById(Integer id) {
        Optional<Dispositivo> dispositivoOpt = dispositivoRepository.findById(id);
        if (dispositivoOpt.isPresent()) {
            Dispositivo dispositivo = dispositivoOpt.get();
            if (dispositivo.getOrdenesReparacion() != null && !dispositivo.getOrdenesReparacion().isEmpty()) {
                // No se puede eliminar si tiene órdenes asociadas
                return false;
            }
            dispositivoRepository.delete(dispositivo);
            return true;
        }
        return false;
    }

    // --- MÉTODOS CORREGIDOS PARA LA HU: VISUALIZAR RESUMEN DE DISPOSITIVOS ---

    @Transactional(readOnly = true)
    public List<Dispositivo> findDispositivosForSummary(List<OrdenReparacion.EstadoOrden> estados, LocalDate startDate, LocalDate endDate) { // CORREGIDO: Tipo de 'estados'
        if (estados != null && !estados.isEmpty() && startDate != null && endDate != null) {
            return dispositivoRepository.findDispositivosByFechaRegistroBetweenAndLastOrdenReparacionEstado(startDate, endDate, estados);
        } else if (estados != null && !estados.isEmpty()) {
            return dispositivoRepository.findDispositivosByLastOrdenReparacionEstado(estados);
        } else if (startDate != null && endDate != null) {
            return dispositivoRepository.findByFechaRegistroBetween(startDate, endDate);
        } else {
            return dispositivoRepository.findAll();
        }
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getDispositivoCountsByEstado() {
        List<Object[]> results = dispositivoRepository.countDispositivosByLastOrdenReparacionEstado();
        return results.stream()
                .collect(Collectors.toMap(
                        obj -> ((OrdenReparacion.EstadoOrden) obj[0]).getDisplayValue(), // CORREGIDO: Obtener displayValue del enum
                        obj -> (Long) obj[1]
                ));
    }
}