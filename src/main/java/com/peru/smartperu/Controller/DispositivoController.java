package com.peru.smartperu.Controller;

import com.peru.smartperu.dto.DispositivoDto;
import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.model.OrdenReparacion; // AÑADIDO: Importar para acceder al enum EstadoOrden
import com.peru.smartperu.service.ClienteService;
import com.peru.smartperu.service.DispositivoService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList; // AÑADIDO: Para crear la lista de enums
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // AÑADIDO: Para usar en la conversión de estados

@Controller
@AllArgsConstructor
@RequestMapping("/dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;
    private final ClienteService clienteService;

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "searchImei", required = false) String searchImei,
                        @RequestParam(value = "searchCliente", required = false) String searchCliente,
                        @RequestParam(value = "searchTipo", required = false) String searchTipo) {

        List<Dispositivo> dispositivos;

        if (searchImei != null && !searchImei.trim().isEmpty()) {
            dispositivos = dispositivoService.searchByImei(searchImei);
        } else if (searchCliente != null && !searchCliente.trim().isEmpty()) {
            dispositivos = dispositivoService.searchByClienteNombre(searchCliente);
        } else if (searchTipo != null && !searchTipo.trim().isEmpty()) {
            dispositivos = dispositivoService.searchByTipoDispositivo(searchTipo);
        } else {
            dispositivos = dispositivoService.findAll();
        }

        model.addAttribute("dispositivos", dispositivos);
        model.addAttribute("searchImei", searchImei);
        model.addAttribute("searchCliente", searchCliente);
        model.addAttribute("searchTipo", searchTipo);

        if (dispositivos.isEmpty() && (searchImei != null || searchCliente != null || searchTipo != null)) {
            model.addAttribute("noResultsMessage", "No se encontraron dispositivos que coincidan con su búsqueda.");
        }

        return "dispositivos/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("dispositivo", new DispositivoDto());
        model.addAttribute("clientes", clienteService.findAll());
        return "dispositivos/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("dispositivo") DispositivoDto dto,
                       RedirectAttributes redirectAttributes, Model model) {
        try {
            Cliente cliente = clienteService.findById(dto.getIdCliente());
            if (cliente == null) {
                model.addAttribute("errorMessage", "El cliente seleccionado no existe.");
                model.addAttribute("dispositivo", dto);
                model.addAttribute("clientes", clienteService.findAll());
                return "dispositivos/create";
            }

            if (dispositivoService.existsByNumeroSerieImei(dto.getNumeroSerieImei())) {
                model.addAttribute("errorMessage", "Ya existe un dispositivo con ese número de serie o IMEI.");
                model.addAttribute("dispositivo", dto);
                model.addAttribute("clientes", clienteService.findAll());
                return "dispositivos/create";
            }

            Dispositivo dispositivo = new Dispositivo();
            dispositivo.setCliente(cliente);
            dispositivo.setTipoDispositivo(dto.getTipoDispositivo());
            dispositivo.setMarca(dto.getMarca());
            dispositivo.setModelo(dto.getModelo());
            dispositivo.setNumeroSerieImei(dto.getNumeroSerieImei());
            dispositivo.setColor(dto.getColor());
            dispositivo.setDescripcionProblemaInicial(dto.getDescripcionProblemaInicial());
            dispositivo.setObservacionesAdicionales(dto.getObservacionesAdicionales());
            dispositivo.setFechaRegistro(dto.getFechaRegistro() != null ? dto.getFechaRegistro() : LocalDate.now());

            dispositivoService.save(dispositivo);
            redirectAttributes.addFlashAttribute("successMessage", "Dispositivo registrado correctamente.");
            return "redirect:/dispositivos";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al registrar el dispositivo: " + e.getMessage());
            model.addAttribute("dispositivo", dto);
            model.addAttribute("clientes", clienteService.findAll());
            return "dispositivos/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Dispositivo dispositivo = dispositivoService.findById(id);
        if (dispositivo == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Dispositivo no encontrado.");
            return "redirect:/dispositivos";
        }
        if (dispositivo.getCliente() == null) {
            System.err.println("ERROR: El dispositivo " + id + " tiene un cliente nulo.");
            redirectAttributes.addFlashAttribute("errorMessage", "Error: El dispositivo no tiene un cliente asociado válido.");
            return "redirect:/dispositivos";
        }
        model.addAttribute("dispositivo", dispositivo);
        return "dispositivos/edit";
    }

    @PostMapping("/update")
    public String updateDispositivo(@ModelAttribute("dispositivo") Dispositivo dispositivo,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (dispositivo.getIdDispositivo() == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "ID de dispositivo no proporcionado para la actualización.");
                return "redirect:/dispositivos";
            }

            if (dispositivo.getMarca() == null || dispositivo.getMarca().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "La Marca es un campo obligatorio y no puede estar vacío.");
                return "redirect:/dispositivos/edit/" + dispositivo.getIdDispositivo();
            }
            if (dispositivo.getModelo() == null || dispositivo.getModelo().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "El Modelo es un campo obligatorio y no puede estar vacío.");
                return "redirect:/dispositivos/edit/" + dispositivo.getIdDispositivo();
            }

            Dispositivo updated = dispositivoService.updateDispositivo(dispositivo.getIdDispositivo(), dispositivo);

            if (updated == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Dispositivo no encontrado para actualizar.");
                return "redirect:/dispositivos";
            }

            redirectAttributes.addFlashAttribute("successMessage", "Dispositivo actualizado correctamente.");
            return "redirect:/dispositivos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar el dispositivo: " + e.getMessage());
            return "redirect:/dispositivos/edit/" + dispositivo.getIdDispositivo();
        }
    }

    @GetMapping("/details/{id}")
    public String showDispositivoDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Dispositivo dispositivo = dispositivoService.findById(id);
        if (dispositivo == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Dispositivo no encontrado.");
            return "redirect:/dispositivos";
        }
        if (dispositivo.getCliente() == null) {
            System.err.println("ERROR: El dispositivo " + id + " tiene un cliente nulo en los detalles.");
            redirectAttributes.addFlashAttribute("errorMessage", "Error: El dispositivo no tiene un cliente asociado válido para mostrar detalles.");
            return "redirect:/dispositivos";
        }

        model.addAttribute("dispositivo", dispositivo);
        return "dispositivos/details";
    }

    // --- MÉTODO CORREGIDO PARA LA HU: VISUALIZAR RESUMEN DE DISPOSITIVOS ---
    @GetMapping("/resumen")
    public String resumen(Model model,
                          @RequestParam(value = "estados", required = false) List<String> estadosString, // Recibimos como String
                          @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                          @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<OrdenReparacion.EstadoOrden> estadosEnum = null;
        if (estadosString != null && !estadosString.isEmpty()) {
            estadosEnum = estadosString.stream()
                    .map(OrdenReparacion.EstadoOrden::valueOf) // Convertir String a Enum
                    .collect(Collectors.toList());
        }

        Map<String, Long> countsByEstado = dispositivoService.getDispositivoCountsByEstado();
        model.addAttribute("countsByEstado", countsByEstado);

        // Lista de todos los posibles valores de displayValue del enum para el select
        List<String> allPossibleDisplayStates = Arrays.stream(OrdenReparacion.EstadoOrden.values())
                .map(OrdenReparacion.EstadoOrden::getDisplayValue)
                .collect(Collectors.toList());
        model.addAttribute("allPossibleStates", allPossibleDisplayStates);


        List<Dispositivo> dispositivosResumen = dispositivoService.findDispositivosForSummary(estadosEnum, startDate, endDate); // Pasamos la lista de Enums
        model.addAttribute("dispositivos", dispositivosResumen);

        model.addAttribute("selectedEstados", estadosString); // Mantenemos la lista de Strings para que el select se seleccione
        model.addAttribute("selectedStartDate", startDate);
        model.addAttribute("selectedEndDate", endDate);

        if (dispositivosResumen.isEmpty() && (estadosString != null && !estadosString.isEmpty() || startDate != null || endDate != null)) {
            model.addAttribute("noResultsMessage", "No se encontraron dispositivos que coincidan con los filtros aplicados.");
        }

        return "dispositivos/resumen";
    }

    // --- NUEVO MÉTODO PARA ELIMINAR ---
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = dispositivoService.deleteById(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Dispositivo eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el dispositivo. Puede tener órdenes de reparación asociadas.");
        }
        return "redirect:/dispositivos";
    }
}