package com.peru.smartperu.service;

import com.peru.smartperu.model.SmartPhone;
import com.peru.smartperu.repository.SmartPhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SmartPhoneService {

    private final SmartPhoneRepository smartPhoneRepository;

    public List<SmartPhone> findAll() {
        return smartPhoneRepository.findAll();
    }

    public SmartPhone findById(Integer id) {
        return smartPhoneRepository.findById(id).orElse(null);
    }

    public void save(SmartPhone smartPhone) {
        smartPhoneRepository.save(smartPhone);
    }
}
