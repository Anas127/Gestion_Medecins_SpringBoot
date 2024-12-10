package com.example.Project.repositories;

import com.example.Project.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    List<Patient> findByMaladie(String maladie);
    List<Patient> findByNomContains(String nom);
    Page<Patient> findByNomContains(String name, Pageable pageable);
}
