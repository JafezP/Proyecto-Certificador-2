package com.peru.smartperu.Controller;

import com.peru.smartperu.model.OrdenReparacion;
import com.peru.smartperu.model.Tecnico;
import com.peru.smartperu.service.TecnicoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    // Lista todos los tecnicos que esten activos
    @GetMapping
    public String index(Model model) {
        model.addAttribute("tecnicos", tecnicoService.findAll());
        return "tecnicos/index";
    }

    // Muestra formulario para crear un nuevo tecnico
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("tecnicos", new Tecnico());
        return "tecnicos/create";
    }

    // Guarda un nuevo tecnico
    @PostMapping("/save")
    public String save(@ModelAttribute("tecnicos") Tecnico tecnico, RedirectAttributes redirectAttributes) {
        tecnico.setFechaRegistro(LocalDate.now());
        tecnicoService.save(tecnico);

        redirectAttributes.addFlashAttribute("successSave", "Los datos del técnico han sido guardados correctamente.");
        return "redirect:/tecnicos";
    }

    // Actualiza informacion de un tecnico
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("tecnico", tecnicoService.findById(id));
        return "tecnicos/edit";
    }

    // Actualiza el estado de un tecnico (lo elimina de la lista, no de la base de datos)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Tecnico tecnico = tecnicoService.findById(id);
        boolean tieneOrdenesActivas = tecnico.getOrdenes().stream()
                .anyMatch(orden ->
                        orden.getEstadoOrden() == OrdenReparacion.EstadoOrden.ASIGNADA ||
                                orden.getEstadoOrden() == OrdenReparacion.EstadoOrden.EN_REPARACION ||
                                orden.getEstadoOrden() == OrdenReparacion.EstadoOrden.ESPERANDO_REPUESTO
                );

        if (tieneOrdenesActivas) {
            redirectAttributes.addFlashAttribute("errorDel", "El técnico tiene órdenes activas.");
            return "redirect:/tecnicos";
        }

        tecnico.setActivo(false);
        tecnicoService.save(tecnico);

        redirectAttributes.addFlashAttribute("successDel", "Los datos del técnico han sido eliminados de la lista correctamente.");
        return "redirect:/tecnicos";
    }

    // Muestra el perfil de un tecnico
    @GetMapping("/profile/{id}")
    public String perfilTecnico(Model model, @PathVariable Integer id) {
        Tecnico tecnico = tecnicoService.findById(id);
        model.addAttribute("tecnico", tecnico);
        model.addAttribute("ordenes", tecnico.getOrdenes());
        return "tecnicos/profile";
    }
}