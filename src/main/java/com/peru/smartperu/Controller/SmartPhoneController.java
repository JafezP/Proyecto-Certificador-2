package com.peru.smartperu.Controller;

import com.peru.smartperu.model.SmartPhone;
import com.peru.smartperu.service.SmartPhoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("smartphones")
public class SmartPhoneController {

    private final SmartPhoneService smartPhoneService;

    // Método existente para listar todos los smartphones (índice)
    // Método index modificado para manejar la búsqueda
    @GetMapping
    public String index(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<SmartPhone> smartphones = smartPhoneService.search(keyword);
        model.addAttribute("smartphones", smartphones);
        model.addAttribute("keyword", keyword); // Mantener el valor de búsqueda en la barra
        return "smartphones/index";
    }


    // Método para ver el detalle de un smartphone (ya lo tienes)
    @GetMapping("/{id}")
    public String viewDetails(@PathVariable("id") Integer id, Model model) {
        SmartPhone smartPhone = smartPhoneService.findById(id);
        if (smartPhone == null) {
            // Manejar caso donde el smartphone no se encuentra
            // Podrías redirigir a una página de error o a la lista de smartphones
            return "redirect:/smartphones"; // Redirige al listado si no se encuentra
        }
        model.addAttribute("smartPhone", smartPhone); // Añade el smartphone al modelo
        return "smartphones/details"; // Devuelve la vista de detalles
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("smartphones", new SmartPhone());
        return "smartphones/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("smartphones") SmartPhone smartPhone) {
        smartPhoneService.save(smartPhone);
        return "redirect:/smartphones";
    }
}