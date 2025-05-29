package com.peru.smartperu.Controller;

import com.peru.smartperu.model.Repuesto;
import com.peru.smartperu.service.RepuestoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/repuestos")
public class RepuestoController {

    private final RepuestoService repuestoService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("repuestos", repuestoService.findAll());
        return "repuestos/index";
    }

    @GetMapping("/create")
    public  String create(Model model) {
        model.addAttribute("repuesto", new Repuesto());
        return "roomstatus/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("roomstatus") Repuesto repuesto) {
        repuestoService.save(repuesto);
        return "redirect:/repuestos";
    }
}
