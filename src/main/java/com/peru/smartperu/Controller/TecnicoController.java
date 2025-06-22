package com.peru.smartperu.Controller;

import com.peru.smartperu.model.Tecnico;
import com.peru.smartperu.service.TecnicoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tecnicos", tecnicoService.findAll());
        return "tecnicos/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("tecnicos", new Tecnico());
        return "tecnicos/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("tecnicos") Tecnico tecnico, RedirectAttributes redirectAttributes) {
        tecnicoService.save(tecnico);
        redirectAttributes.addFlashAttribute("success", "¡Técnico guardado correctamente!");
        return "redirect:/tecnicos";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("tecnico", tecnicoService.findById(id));
        return "tecnicos/edit";
    }
}