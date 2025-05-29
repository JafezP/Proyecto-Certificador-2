package com.peru.smartperu.service;


import com.peru.smartperu.model.Dispositivo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DispositivoService {
    private final DispositivoService dispositivoService;

    public List<Dispositivo> findAll() {
        return dispositivoService.findAll();
    }
    public Dispositivo findById(Integer id) {
        return dispositivoService.findById(id);
    }
    public Dispositivo save(Dispositivo dispositivo) {
        return dispositivoService.save(dispositivo);
    }
}
