package com.peru.smartperu.repository;

import com.peru.smartperu.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importar Query
import org.springframework.data.repository.query.Param; // Importar Param
import java.util.List;

public interface DispositvoRepository extends JpaRepository<Dispositivo, Integer> {
    boolean existsByNumeroSerieImei(String imei);

    // 1. Buscar por IMEI (parcial, ignorando mayúsculas/minúsculas)
    List<Dispositivo> findByNumeroSerieImeiContainingIgnoreCase(String imei);

    // 2. Buscar por nombre/apellido del cliente (parcial, ignorando mayúsculas/minúsculas)
    // Usamos @Query para búsquedas en relaciones o concatenaciones
    @Query("SELECT d FROM Dispositivo d WHERE LOWER(d.cliente.nombreCompleto) LIKE LOWER(CONCAT('%', :nombreCliente, '%'))")
    List<Dispositivo> findByClienteNombreCompletoContainingIgnoreCase(@Param("nombreCliente") String nombreCliente);

    // 3. Buscar por tipo de dispositivo (parcial, ignorando mayúsculas/minúsculas)
    List<Dispositivo> findByTipoDispositivoContainingIgnoreCase(String tipo);

    // Opcional: Buscar por múltiples criterios (si se implementa en la UI)
    @Query("SELECT d FROM Dispositivo d WHERE " +
            "(LOWER(d.numeroSerieImei) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(d.cliente.nombreCompleto) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(d.tipoDispositivo) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    List<Dispositivo> searchDispositivosByKeyword(@Param("searchText") String searchText);

    // Opcional: Para una búsqueda más flexible con campos específicos
    List<Dispositivo> findByNumeroSerieImeiContainingIgnoreCaseAndClienteNombreCompletoContainingIgnoreCaseAndTipoDispositivoContainingIgnoreCase(String imei, String clienteNombre, String tipoDispositivo);
}