package com.example.Project.web;

import com.example.Project.entities.Medecin;
import com.example.Project.repositories.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
public class MedecinRestController {

    @Autowired
    private MedecinRepository medecinRepository;


    @GetMapping
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }


    @GetMapping("/{id}")
    public Medecin getMedecinById(@PathVariable Integer id) {
        return medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medecin not found with id: " + id));
    }


    @PostMapping
    public Medecin createMedecin(@RequestBody Medecin medecin) {
        return medecinRepository.save(medecin);
    }


    @PutMapping("/{id}")
    public Medecin updateMedecin(@PathVariable Integer id, @RequestBody Medecin updatedMedecin) {
        return medecinRepository.findById(id).map(medecin -> {
            medecin.setNom(updatedMedecin.getNom());
            medecin.setSalaire(updatedMedecin.getSalaire());
            medecin.setPatient(updatedMedecin.getPatient());
            return medecinRepository.save(medecin);
        }).orElseThrow(() -> new RuntimeException("Medecin not found with id: " + id));
    }


    @DeleteMapping("/{id}")
    public void deleteMedecin(@PathVariable Integer id) {
        medecinRepository.deleteById(id);
    }
}