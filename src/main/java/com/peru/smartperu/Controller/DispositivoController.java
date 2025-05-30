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
@AllArgsConstructor // Inyecta automáticamente DispositivoService
@RequestMapping("/dispositivos")
public class DispositivoController {
    // Inyecta el servicio correctamente.
    private final DispositivoService dispositivoService;

    @GetMapping // Muestra la lista de dispositivos
    public String index(Model model) {
        model.addAttribute("dispositivos", dispositivoService.findAll());
        return "dispositivos/index"; // Retorna la vista index.html para dispositivos
    }

    @GetMapping("/create") // Muestra el formulario de creación
    public String create(Model model) {
        // CAMBIO CLAVE: Usa "dispositivo" (singular) para el objeto nuevo
        model.addAttribute("dispositivo", new Dispositivo());
        return "dispositivos/create"; // Retorna la vista create.html para dispositivos
    }

    // CAMBIO CLAVE: Debe ser @PostMapping para procesar formularios enviados
    // Usa @ModelAttribute("dispositivo") para enlazar los datos del formulario al objeto Dispositivo
    @PostMapping("/save")
    public String save(@ModelAttribute("dispositivo") Dispositivo dispositivo) {
        dispositivoService.save(dispositivo);
        return "redirect:/dispositivos"; // Redirige a la lista de dispositivos después de guardar
    }
}