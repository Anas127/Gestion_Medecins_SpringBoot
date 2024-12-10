package com.example.Project.web;

import com.example.Project.entities.Patient;
import com.example.Project.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientRestController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Integer id, @RequestBody Patient updatedPatient) {
        return patientRepository.findById(id).map(patient -> {
            patient.setNom(updatedPatient.getNom());
            patient.setMaladie(updatedPatient.getMaladie());
            patient.setMedecin(updatedPatient.getMedecin());
            return patientRepository.save(patient);
        }).orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Integer id) {
        patientRepository.deleteById(id);
    }
}
