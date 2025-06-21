package com.peru.smartperu.Controller;

import com.peru.smartperu.dto.DispositivoDto;
import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.model.OrdenReparacion; // <<< AÑADE ESTE IMPORT para usarlo en la vista si es necesario
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
            // Asegúrate de establecer la fecha de registro si es nula en el DTO
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

    // Métodos para HU04: Actualizar Dispositivo (no modificados sustancialmente para HU05)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Dispositivo dispositivo = dispositivoService.findById(id);
        if (dispositivo == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Dispositivo no encontrado.");
            return "redirect:/dispositivos";
        }
        // Asegúrate de que el cliente no sea nulo al cargar para editar (defensa)
        if (dispositivo.getCliente() == null) {
            System.err.println("ERROR: El dispositivo " + id + " tiene un cliente nulo.");
            // Considera redirigir o mostrar un error más amigable si esto es un estado inválido.
            redirectAttributes.addFlashAttribute("errorMessage", "Error: El dispositivo no tiene un cliente asociado válido.");
            return "redirect:/dispositivos"; // Redirigir para evitar errores en la vista.
        }
        model.addAttribute("dispositivo", dispositivo);
        // Si necesitas la lista de clientes para un select en el formulario de edición:
        // model.addAttribute("clientes", clienteService.findAll());
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

            // Aquí puedes añadir validaciones adicionales antes de actualizar.
            if (dispositivo.getMarca() == null || dispositivo.getMarca().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "La Marca es un campo obligatorio y no puede estar vacío.");
                return "redirect:/dispositivos/edit/" + dispositivo.getIdDispositivo(); // Redirigir de vuelta al formulario de edición con el error
            }
            if (dispositivo.getModelo() == null || dispositivo.getModelo().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "El Modelo es un campo obligatorio y no puede estar vacío.");
                return "redirect:/dispositivos/edit/" + dispositivo.getIdDispositivo();
            }

            // Llama al servicio para actualizar
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

    // --- NUEVO MÉTODO PARA LA HU05: MOSTRAR DETALLE DEL DISPOSITIVO ---
    @GetMapping("/details/{id}")
    public String showDispositivoDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Dispositivo dispositivo = dispositivoService.findById(id);
        if (dispositivo == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Dispositivo no encontrado.");
            return "redirect:/dispositivos";
        }
        // Asegúrate de que el cliente no sea nulo al mostrar detalles (defensa)
        if (dispositivo.getCliente() == null) {
            System.err.println("ERROR: El dispositivo " + id + " tiene un cliente nulo en los detalles.");
            redirectAttributes.addFlashAttribute("errorMessage", "Error: El dispositivo no tiene un cliente asociado válido para mostrar detalles.");
            return "redirect:/dispositivos";
        }

        // Las órdenes de reparación se cargarán automáticamente al acceder a dispositivo.getOrdenesReparacion() en la vista
        model.addAttribute("dispositivo", dispositivo);
        return "dispositivos/details"; // Esta es la nueva plantilla HTML
    }
}