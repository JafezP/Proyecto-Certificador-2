package com.peru.smartperu.repository;

import com.peru.smartperu.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

// --- CAMBIO DE NOMBRE DEL REPOSITORIO ---
public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {
    boolean existsByNumeroSerieImei(String imei);

    List<Dispositivo> findByNumeroSerieImeiContainingIgnoreCase(String imei);

    @Query("SELECT d FROM Dispositivo d WHERE LOWER(d.cliente.nombreCompleto) LIKE LOWER(CONCAT('%', :nombreCliente, '%'))")
    List<Dispositivo> findByClienteNombreCompletoContainingIgnoreCase(@Param("nombreCliente") String nombreCliente);

    List<Dispositivo> findByTipoDispositivoContainingIgnoreCase(String tipo);

    @Query("SELECT d FROM Dispositivo d WHERE " +
            "(LOWER(d.numeroSerieImei) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(d.cliente.nombreCompleto) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(d.tipoDispositivo) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    List<Dispositivo> searchDispositivosByKeyword(@Param("searchText") String searchText);

    List<Dispositivo> findByNumeroSerieImeiContainingIgnoreCaseAndClienteNombreCompletoContainingIgnoreCaseAndTipoDispositivoContainingIgnoreCase(String imei, String clienteNombre, String tipoDispositivo);
}