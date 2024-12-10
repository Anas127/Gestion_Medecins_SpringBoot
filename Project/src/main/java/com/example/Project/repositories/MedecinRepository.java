package com.example.Project.repositories;

import com.example.Project.entities.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Integer> {

    List<Medecin> findBySalaire(double salaire);
    List<Medecin> findBySalaireBetween(double salaire1, double salaire2);
    Page<Medecin> findByNomContains(String name, Pageable pageable);

}
