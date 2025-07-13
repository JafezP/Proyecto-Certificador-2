// src/main/java/com/peru/smartperu/Controller/OrdenReparacionController.java
package com.peru.smartperu.Controller;

import com.peru.smartperu.dto.OrdenReparacionDto;
import com.peru.smartperu.model.OrdenReparacion;
import com.peru.smartperu.service.OrdenReparacionService;
import com.peru.smartperu.service.DispositivoService; // Para el select de dispositivos
import com.peru.smartperu.service.ClienteService;     // Para el select de clientes
import com.peru.smartperu.service.TecnicoService;     // Para el select de técnicos

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.peru.smartperu.model.OrdenReparacion.EstadoOrden; // Importa el Enum

@Controller
@AllArgsConstructor
@RequestMapping("/ordenes") // Ruta base para las órdenes de reparación
public class OrdenReparacionController {

    private final OrdenReparacionService ordenReparacionService;
    private final DispositivoService dispositivoService;
    private final ClienteService clienteService;
    private final TecnicoService tecnicoService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ordenes", ordenReparacionService.findAll());
        return "ordenes/index"; // Vista para listar órdenes
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("orden", new OrdenReparacionDto());
        // Necesitamos pasar las listas para los selects
        model.addAttribute("dispositivos", dispositivoService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("tecnicos", tecnicoService.findAll());
        model.addAttribute("estadosOrden", EstadoOrden.values()); // Pasa todos los valores del Enum
        return "ordenes/create"; // Vista para crear una orden
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("orden") OrdenReparacionDto ordenDto,
                       RedirectAttributes redirectAttributes) {
        try {
            ordenReparacionService.save(ordenDto);
            redirectAttributes.addFlashAttribute("successMessage", "Orden de Reparación registrada exitosamente!");
            return "redirect:/ordenes";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

            // Redirige al formulario correcto (crear o editar)
            if (ordenDto.getIdOrden() != null) {
                return "redirect:/ordenes/edit/" + ordenDto.getIdOrden();
            } else {
                return "redirect:/ordenes/create";
            }
        }
//        try {
//            ordenReparacionService.save(ordenDto);
//            redirectAttributes.addFlashAttribute("successMessage", "Orden de Reparación registrada exitosamente!");
//            return "redirect:/ordenes";
//        } catch (RuntimeException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Error al registrar la orden: " + e.getMessage());
//            return "redirect:/ordenes/create";
//        }
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        OrdenReparacion orden = ordenReparacionService.findById(id);
        OrdenReparacionDto dto = ordenReparacionService.convertToDto(orden);

        model.addAttribute("orden", dto); // ahora es DTO
        model.addAttribute("dispositivos", dispositivoService.findAll());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("tecnicos", tecnicoService.findAll());
        model.addAttribute("estadosOrden", EstadoOrden.values());

        return "ordenes/edit";
    }



    // Aquí podrías añadir métodos para 'view', 'edit', 'delete' más adelante
}