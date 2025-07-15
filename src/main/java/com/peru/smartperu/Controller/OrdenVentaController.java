package com.peru.smartperu.Controller;

import com.peru.smartperu.model.ItemOrdenVenta;
import com.peru.smartperu.model.OrdenVenta;
import com.peru.smartperu.model.SmartPhone;
import com.peru.smartperu.service.ClienteService;
import com.peru.smartperu.service.OrdenVentaService;
import com.peru.smartperu.service.SmartPhoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("/ventas")
public class OrdenVentaController {

    private final OrdenVentaService ordenVentaService;
    private final ClienteService clienteService;
    private final SmartPhoneService smartPhoneService;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("ventas", ordenVentaService.findAll());
        return "ventas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ordenVenta", new OrdenVenta());
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("smartphones", smartPhoneService.listarDisponibles());
        return "ventas/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute OrdenVenta ordenVenta, RedirectAttributes redirectAttributes) {
        ordenVenta.setFecha(LocalDate.now());

        double subtotal = ordenVenta.getItems().stream()
                .mapToDouble(ItemOrdenVenta::getSubtotal)
                .sum();

        double impuesto = subtotal * 0.18;
        double total = subtotal + impuesto;

        ordenVenta.setSubtotal(subtotal);
        ordenVenta.setImpuesto(impuesto);
        ordenVenta.setTotal(total);

        ordenVenta.getItems().forEach(item -> item.setOrdenVenta(ordenVenta));
        ordenVentaService.save(ordenVenta);

        ordenVenta.getItems().forEach(item -> {
            SmartPhone smartphone = smartPhoneService.buscarPorId(item.getDescripcionId()); // usa ID
            if (smartphone != null) {
                smartPhoneService.actualizarStock(smartphone);
            }
        });

        redirectAttributes.addFlashAttribute("successMessage", "Orden de venta registrada correctamente.");
        return "redirect:/ventas";
    }

}

