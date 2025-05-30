package com.peru.smartperu.Controller;

import com.peru.smartperu.model.SmartPhone;
import com.peru.smartperu.service.SmartPhoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("smartphones")
public class SmartPhoneController {

    private final SmartPhoneService smartPhoneService;

    public String index(Model model) {
        model.addAttribute("smartphones", smartPhoneService.findAll());
        return "smartphones/index";
    }

    @GetMapping("/create")
    public  String create(Model model) {
        model.addAttribute("smartphones", new SmartPhone());
        return "smartphones/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("smartphones") SmartPhone smartPhone) {
        smartPhoneService.save(smartPhone);
        return "redirect:/smartphones";
    }
}
