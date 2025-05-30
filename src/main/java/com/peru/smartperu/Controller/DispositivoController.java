package com.peru.smartperu.Controller;

import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.service.DispositivoService; // Asegúrate de que este import sea correcto
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // Necesario para @ModelAttribute
import org.springframework.web.bind.annotation.PostMapping; // Necesario para @PostMapping
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("dispositivos", dispositivoService.findAll());
        return "dispositivos/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("dispositivos", new Dispositivo());
        return "dispositivos/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("dispositivos") Dispositivo dispositivo) {
        dispositivoService.save(dispositivo);
        return "redirect:/dispositivos";
    }
}