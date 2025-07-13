package com.peru.smartperu.Controller;

import com.peru.smartperu.model.Repuesto;
import com.peru.smartperu.model.Tecnico; // Si Tecnico no se usa, puedes quitar esta importación
import com.peru.smartperu.service.RepuestoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List; // Importar List

@Controller
@AllArgsConstructor
@RequestMapping("/repuestos") // Ajuste la ruta principal a "/repuestos" para ser más consistente
public class RepuestoController {

    private final RepuestoService repuestoService;

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "search", required = false) String searchText) { // Nuevo parámetro de búsqueda
        List<Repuesto> repuestos;
        String noResultsMessage = null;

        if (searchText != null && !searchText.trim().isEmpty()) {
            repuestos = repuestoService.searchRepuestos(searchText);
            if (repuestos.isEmpty()) {
                noResultsMessage = "No se encontraron repuestos con los criterios de búsqueda: '" + searchText + "'.";
            }
        } else {
            repuestos = repuestoService.findAll();
        }

        model.addAttribute("repuestos", repuestos);
        model.addAttribute("searchText", searchText); // Para mantener el texto en el campo de búsqueda
        model.addAttribute("noResultsMessage", noResultsMessage); // Mensaje de no resultados
        return "repuestos/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("repuestos", new Repuesto());
        return "repuestos/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("repuestos") Repuesto repuesto, RedirectAttributes redirectAttributes) {
        // Asegúrate de que los campos de cantidad y precio no sean nulos o vacíos antes de guardar
        // Puedes añadir validaciones más robustas aquí o en un DTO con @Valid
        if (repuesto.getFechaRegistro() == null) {
            repuesto.setFechaRegistro(LocalDate.now());
        }
        repuestoService.save(repuesto);

        redirectAttributes.addFlashAttribute("successSave", "Los datos del repuesto han sido guardados correctamente.");
        return "redirect:/repuestos";
    }

    // Actualiza informacion de un repuesto
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("repuesto", repuestoService.findById(id));
        return "repuestos/edit";
    }

    // Muestra informacion de un repuesto
    @GetMapping("/profile/{id}")
    public String infoRepuesto(Model model, @PathVariable Integer id) {
        Repuesto repuesto = repuestoService.findById(id);
        model.addAttribute("repuesto", repuesto);
//        model.addAttribute("ordenes", tecnico.getOrdenes()); // Comentado si Tecnico no se usa aquí
        return "repuestos/profile";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            repuestoService.deleteById(id); // Llama al servicio para eliminar
            redirectAttributes.addFlashAttribute("successDel", "El repuesto ha sido eliminado correctamente del inventario.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorDel", "No se pudo eliminar el repuesto. Ha ocurrido un error.");
        }
        return "redirect:/repuestos";
    }
}