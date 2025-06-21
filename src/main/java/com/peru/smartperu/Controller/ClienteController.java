package com.peru.smartperu.Controller;


import ch.qos.logback.core.net.server.Client;
import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/delete/{id}") // Usar POST para acciones que modifican datos
    public String deleteCliente(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean deleted = clienteService.deleteCliente(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Cliente eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el cliente. Verifique si tiene órdenes de reparación o compras asociadas.");
        }
        return "redirect:/clientes";
    }

    @GetMapping("/details/{id}")
    public String showClienteDetails(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cliente no encontrado.");
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente);
        return "clientes/details"; // Esta es la nueva plantilla HTML
    }
}
