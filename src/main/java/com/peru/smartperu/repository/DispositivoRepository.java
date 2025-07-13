package com.peru.smartperu.repository;

import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.model.OrdenReparacion; // Importar OrdenReparacion para el enum
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

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

    // --- NUEVOS MÉTODOS PARA LA HU: VISUALIZAR RESUMEN DE DISPOSITIVOS ---

    // 1. Buscar dispositivos por rango de fechas de registro
    List<Dispositivo> findByFechaRegistroBetween(LocalDate startDate, LocalDate endDate);

    // 2. Buscar dispositivos por el estado de su ÚLTIMA orden de reparación
    @Query("SELECT d FROM Dispositivo d JOIN d.ordenesReparacion orp " +
            "WHERE orp.estadoOrden IN :estados " + // CORREGIDO: orp.estadoOrden
            "AND orp.fechaCreacion = (SELECT MAX(orp2.fechaCreacion) FROM OrdenReparacion orp2 WHERE orp2.dispositivo.idDispositivo = d.idDispositivo)")
    List<Dispositivo> findDispositivosByLastOrdenReparacionEstado(@Param("estados") List<OrdenReparacion.EstadoOrden> estados); // CORREGIDO: List<OrdenReparacion.EstadoOrden>

    // 3. Método para obtener dispositivos por rango de fecha Y estado de la última orden (combinado)
    @Query("SELECT d FROM Dispositivo d JOIN d.ordenesReparacion orp " +
            "WHERE d.fechaRegistro BETWEEN :startDate AND :endDate " +
            "AND orp.estadoOrden IN :estados " + // CORREGIDO: orp.estadoOrden
            "AND orp.fechaCreacion = (SELECT MAX(orp2.fechaCreacion) FROM OrdenReparacion orp2 WHERE orp2.dispositivo.idDispositivo = d.idDispositivo)")
    List<Dispositivo> findDispositivosByFechaRegistroBetweenAndLastOrdenReparacionEstado(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("estados") List<OrdenReparacion.EstadoOrden> estados); // CORREGIDO: List<OrdenReparacion.EstadoOrden>

    // 4. Para el resumen: Contar dispositivos por el estado de su ÚLTIMA orden de reparación.
    @Query("SELECT orp.estadoOrden, COUNT(DISTINCT d.idDispositivo) FROM Dispositivo d JOIN d.ordenesReparacion orp " + // CORREGIDO: orp.estadoOrden
            "WHERE orp.fechaCreacion = (SELECT MAX(orp2.fechaCreacion) FROM OrdenReparacion orp2 WHERE orp2.dispositivo.idDispositivo = d.idDispositivo) " +
            "GROUP BY orp.estadoOrden") // CORREGIDO: orp.estadoOrden
    List<Object[]> countDispositivosByLastOrdenReparacionEstado();
}