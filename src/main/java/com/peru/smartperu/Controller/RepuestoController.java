package com.peru.smartperu.Controller;

import com.peru.smartperu.model.Repuesto;
import com.peru.smartperu.model.Tecnico;
import com.peru.smartperu.service.RepuestoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("repuestos")
public class RepuestoController {

    private final RepuestoService repuestoService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("repuestos", repuestoService.findAll());
        return "repuestos/index";
    }

    @GetMapping("/create")
    public  String create(Model model) {
        model.addAttribute("repuestos", new Repuesto());
        return "repuestos/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("repuestos") Repuesto repuesto, RedirectAttributes redirectAttributes) {
        repuesto.setFechaRegistro(LocalDate.now());
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
//        model.addAttribute("ordenes", tecnico.getOrdenes());
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
