package com.peru.smartperu.Controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class PageController {
    @GetMapping
    public String index() {
        return "index"; // Retorna la vista principal
    }

    @GetMapping("/promociones")
    public String ofertas() {
        return "promociones"; // Retorna la vista de ofertas;
    }
}
