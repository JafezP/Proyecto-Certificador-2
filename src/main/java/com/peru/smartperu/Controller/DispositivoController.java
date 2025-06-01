package com.peru.smartperu.Controller;

import com.peru.smartperu.dto.DispositivoDto;
import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.service.DispositivoService;
import com.peru.smartperu.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate; // Asegúrate de que solo este import esté aquí

@Controller
@AllArgsConstructor
@RequestMapping("dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;
    private final ClienteService clienteService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("dispositivos", dispositivoService.findAll());
        return "dispositivos/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("dispositivo", new DispositivoDto());
        model.addAttribute("clientes", clienteService.findAll());
        return "dispositivos/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("dispositivo") DispositivoDto dispositivoDto,
                       RedirectAttributes redirectAttributes) {
        try {
            Dispositivo dispositivo = new Dispositivo();
            dispositivo.setTipoDispositivo(dispositivoDto.getTipoDispositivo());
            dispositivo.setMarca(dispositivoDto.getMarca());
            dispositivo.setModelo(dispositivoDto.getModelo());
            dispositivo.setNumeroSerieImei(dispositivoDto.getNumeroSerieImei());
            dispositivo.setColor(dispositivoDto.getColor());
            dispositivo.setDescripcionProblemaInicial(dispositivoDto.getDescripcionProblemaInicial());
            dispositivo.setObservacionesAdicionales(dispositivoDto.getObservacionesAdicionales());

            // SIMPLIFICACIÓN: Asignación directa de LocalDate a LocalDate
            if (dispositivoDto.getFechaRegistro() != null) {
                dispositivo.setFechaRegistro(dispositivoDto.getFechaRegistro());
            } else {
                dispositivo.setFechaRegistro(LocalDate.now()); // Establecer fecha actual si no viene
            }

            if (dispositivoDto.getIdCliente() != null) {
                Cliente cliente = clienteService.findById(dispositivoDto.getIdCliente());
                if (cliente == null) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Cliente con ID " + dispositivoDto.getIdCliente() + " no encontrado.");
                    return "redirect:/dispositivos/create";
                }
                dispositivo.setCliente(cliente);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Debe seleccionar un cliente.");
                return "redirect:/dispositivos/create";
            }

            dispositivoService.save(dispositivo);
            redirectAttributes.addFlashAttribute("successMessage", "Dispositivo registrado exitosamente!");
            return "redirect:/dispositivos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al registrar el dispositivo: " + e.getMessage());
            return "redirect:/dispositivos/create";
        }
    }
}