package com.peru.smartperu.repository;

import com.peru.smartperu.model.SmartPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmartPhoneRepository extends JpaRepository<SmartPhone, Integer> {
    List<SmartPhone> findByDisponibleTrue();
}
