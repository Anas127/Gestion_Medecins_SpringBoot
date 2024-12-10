package com.example.Project.services;

import com.example.Project.entities.Medecin;
import com.example.Project.repositories.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {
    @Autowired
    private MedecinRepository medecinRepository;

    public Medecin saveMedecin(Medecin medecin) { return medecinRepository.save(medecin); }

    public List<Medecin> getMedecins() {
        return medecinRepository.findAll();
    }

    public Medecin getMedecinById(Integer id) {
        return medecinRepository.findById(id).orElse(null);
    }


}
