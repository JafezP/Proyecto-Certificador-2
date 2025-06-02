package com.peru.smartperu.Controller;

import com.peru.smartperu.dto.DispositivoDto;
import com.peru.smartperu.model.Cliente;
import com.peru.smartperu.model.Dispositivo;
import com.peru.smartperu.service.ClienteService;
import com.peru.smartperu.service.DispositivoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("/dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;
    private final ClienteService clienteService;

    // Mostrar lista de dispositivos
    @GetMapping
    public String index(Model model) {
        model.addAttribute("dispositivos", dispositivoService.findAll());
        return "dispositivos/index";
    }

    // Mostrar formulario de creación
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("dispositivo", new DispositivoDto());
        model.addAttribute("clientes", clienteService.findAll());
        return "dispositivos/create";
    }

    // Guardar nuevo dispositivo
    @PostMapping("/save")
    public String save(@ModelAttribute("dispositivo") DispositivoDto dto,
                       RedirectAttributes redirectAttributes, Model model) {
        try {
            // Validar que exista el cliente
            Cliente cliente = clienteService.findById(dto.getIdCliente());
            if (cliente == null) {
                model.addAttribute("errorMessage", "El cliente seleccionado no existe.");
                model.addAttribute("dispositivo", dto);
                model.addAttribute("clientes", clienteService.findAll());
                return "dispositivos/create";
            }

            // Verificar si el IMEI ya está registrado
            if (dispositivoService.existsByNumeroSerieImei(dto.getNumeroSerieImei())) {
                model.addAttribute("errorMessage", "Ya existe un dispositivo con ese número de serie o IMEI.");
                model.addAttribute("dispositivo", dto);
                model.addAttribute("clientes", clienteService.findAll());
                return "dispositivos/create";
            }

            // Mapear DTO a entidad
            Dispositivo dispositivo = new Dispositivo();
            dispositivo.setCliente(cliente);
            dispositivo.setTipoDispositivo(dto.getTipoDispositivo());
            dispositivo.setMarca(dto.getMarca());
            dispositivo.setModelo(dto.getModelo());
            dispositivo.setNumeroSerieImei(dto.getNumeroSerieImei());
            dispositivo.setColor(dto.getColor());
            dispositivo.setDescripcionProblemaInicial(dto.getDescripcionProblemaInicial());
            dispositivo.setObservacionesAdicionales(dto.getObservacionesAdicionales());
            dispositivo.setFechaRegistro(dto.getFechaRegistro() != null ? dto.getFechaRegistro() : LocalDate.now());

            dispositivoService.save(dispositivo);
            redirectAttributes.addFlashAttribute("successMessage", "Dispositivo registrado correctamente.");
            return "redirect:/dispositivos";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al registrar el dispositivo: " + e.getMessage());
            model.addAttribute("dispositivo", dto);
            model.addAttribute("clientes", clienteService.findAll());
            return "dispositivos/create";
        }
    }

}
