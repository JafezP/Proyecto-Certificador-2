package com.peru.smartperu.Controller;


import ch.qos.logback.core.net.server.Client;
import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public String index(Model model){
    model.addAttribute("clientes", clienteService.findAll());
    return "clientes/index";
    }

    @GetMapping("/create")
    public String create (Model model){
    model.addAttribute("clientes", new Cliente());
    return "clientes/create";
    }

    @PostMapping("/save")
    public String save (@ModelAttribute("clientes") Cliente cliente){
        clienteService.save(cliente);
        return "redirect:/clientes";

    }
}
