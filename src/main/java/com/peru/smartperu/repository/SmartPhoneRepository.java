package com.peru.smartperu.repository;

import com.peru.smartperu.model.SmartPhone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmartPhoneRepository extends JpaRepository<SmartPhone, Integer> {
}
