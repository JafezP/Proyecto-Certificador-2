package com.peru.smartperu.service;

import com.peru.smartperu.model.Tecnico;
import com.peru.smartperu.repository.TecnicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico findById(Integer id) {
        return tecnicoRepository.findById(id).orElse(null);
    }

    public Tecnico save(Tecnico tecnico) {
        return tecnicoRepository.save(tecnico);
    }
}
