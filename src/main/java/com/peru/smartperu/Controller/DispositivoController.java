package com.peru.smartperu.Controller;

import com.peru.smartperu.model.Dispositivo;
import org.springframework.ui.Model;
import com.peru.smartperu.service.DispositivoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/dispositivos")
public class DispositivoController {
    private final DispositivoService dispositivoService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("dispositivos", dispositivoService.findAll());
        return "dispositivos/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("dispositivo", new Dispositivo());
        return "dispositivos/create";
    }
    @GetMapping("/save")
    public String save(Dispositivo dispositivo) {
        dispositivoService.save(dispositivo);
        return "redirect:/dispositivos";
    }
}
