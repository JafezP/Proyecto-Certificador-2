package com.peru.smartperu.Controller;

import com.peru.smartperu.dto.DispositivoDto;
import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.service.ClienteService;
import com.peru.smartperu.service.DispositivoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;
    private final ClienteService clienteService;

    // Mostrar lista de dispositivos y manejar búsqueda
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

    // Mostrar formulario de creación
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("dispositivo", new DispositivoDto());
        model.addAttribute("clientes", clienteService.findAll());
        return "dispositivos/create";
    }

    // Guardar nuevo dispositivo
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

    // **** MÉTODOS PARA HU04: ACTUALIZAR DISPOSITIVO (AJUSTADO) ****

    // 1. Mostrar el formulario de edición con los datos actuales del dispositivo
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

    // 2. Procesar el envío del formulario de edición
    @PostMapping("/update")
    public String updateDispositivo(@ModelAttribute("dispositivo") Dispositivo dispositivo,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (dispositivo.getIdDispositivo() == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "ID de dispositivo no proporcionado para la actualización.");
                return "redirect:/dispositivos";
            }

            // Validaciones para campos obligatorios que son editables
            if (dispositivo.getMarca() == null || dispositivo.getMarca().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "La Marca es un campo obligatorio y no puede estar vacío.");
                return "redirect:/dispositivos/edit/" + dispositivo.getIdDispositivo();
            }
            if (dispositivo.getModelo() == null || dispositivo.getModelo().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "El Modelo es un campo obligatorio y no puede estar vacío.");
                return "redirect:/dispositivos/edit/" + dispositivo.getIdDispositivo();
            }

            // Llamar al servicio para actualizar el dispositivo con los campos editables
            Dispositivo updated = dispositivoService.updateDispositivo(dispositivo.getIdDispositivo(), dispositivo);

            if (updated == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Dispositivo no encontrado para actualizar.");
                return "redirect:/dispositivos";
            }

            redirectAttributes.addFlashAttribute("successMessage", "Dispositivo actualizado correctamente.");
            return "redirect:/dispositivos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al actualizar el dispositivo: " + e.getMessage());
            // Si hay un error, redirige de nuevo a la página de edición con el mensaje
            return "redirect:/dispositivos/edit/" + dispositivo.getIdDispositivo();
        }
    }
}