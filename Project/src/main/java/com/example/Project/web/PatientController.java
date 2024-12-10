package com.example.Project.web;

import com.example.Project.entities.Medecin;
import com.example.Project.entities.Patient;
import com.example.Project.repositories.PatientRepository;
import com.example.Project.services.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientController {

    @Autowired
    PatientRepository patientRepository;


    @GetMapping(path = "/index2")
    public String patients(Model model) {
        List<Patient> allPatients = patientRepository.findAll();
        model.addAttribute("allPatients", allPatients);
        return "patients";
    }

    @Autowired
    private MedecinService medecinService;

    @PostMapping("/add")
    public String addPatient(@ModelAttribute Patient patient, @RequestParam(name = "medecin.id") Integer medecinId) {
        Medecin medecin = medecinService.getMedecinById(medecinId);
        if (medecin != null) {
            patient.setMedecin(medecin);
        }
        patientRepository.save(patient);
        return "redirect:/patientsList";
    }

    @GetMapping("/createPatient")
    public String showAddPatientForm(Model model) {
        List<Medecin> doctors = medecinService.getMedecins();
        model.addAttribute("doctors", doctors);
        model.addAttribute("patient", new Patient());
        return "addpatient";
    }


    @GetMapping(path = "/patientsList")
    public String allPatients(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "3") int size,
                              @RequestParam(name = "search", defaultValue = "") String searchName) {
        System.out.println("Searching for: " + searchName);
        Page<Patient> pagePatients = patientRepository.findByNomContains(searchName, PageRequest.of(page, size));

        int[] pages = new int[pagePatients.getTotalPages()];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }

        model.addAttribute("pagePatients", pagePatients.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);

        if (pagePatients.isEmpty()) {
            model.addAttribute("noResultsMessage", "No patients found for the search term '" + searchName + "'.");
        }

        return "patients";
    }

    @GetMapping(path = "/deletePatient")
    public String deletePatient(@RequestParam(name = "id") Integer id,
                                @RequestParam(name = "page") int page,
                                @RequestParam(name = "size") int size,
                                @RequestParam(name = "search") String search) {
        patientRepository.deleteById(id);
        return "redirect:/patientsList?page=" + page + "&size=" + size + "&search=" + search;
    }

    @GetMapping(path = "/patientDetails")
    public String showPatientDetails(@RequestParam(name = "id") Integer id, Model model) {
        Patient patient = patientRepository.findById(id).orElse(null);

        if (patient == null) {
            return "redirect:/patientsList";
        }

        model.addAttribute("patient", patient);
        return "patientDetails";
    }

}
