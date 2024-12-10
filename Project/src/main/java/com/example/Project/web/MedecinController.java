package com.example.Project.web;

import com.example.Project.entities.Medecin;
import com.example.Project.entities.Patient;
import com.example.Project.repositories.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller public class MedecinController {
    @Autowired
    MedecinRepository medecinRepository;

    @GetMapping(path = "/index1")
    public String medecins(Model model) {
        List<Medecin> allMedecins = medecinRepository.findAll();
        model.addAttribute("allMedecins", allMedecins);
        return "medecins";
    }

    @GetMapping(path = "/medecinsList")
    public String allMedecins(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "3") int size, @RequestParam(name = "search", defaultValue = "") String searchName) {
        System.out.println("Searching for: " + searchName);
        Page<Medecin> pageMedecins = medecinRepository.findByNomContains(searchName,PageRequest.of(page,size));

        int[] pages = new int[pageMedecins.getTotalPages()];
        for(int i = 0; i < pages.length; i++) {
            pages[i] = i;
        }
        model.addAttribute("pageMedecins", pageMedecins.getContent());
        model.addAttribute("tabPages", pages);
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);

        if (pageMedecins.isEmpty()) {
            model.addAttribute("noResultsMessage", "No doctors found for the search term '" + searchName + "'.");
        }

        return "medecins";
    }

    @GetMapping(path = "/createMedecin")
    public String createMedecin(Model model) {
        Medecin medecin = new Medecin();
        model.addAttribute("medecin", medecin);
        return "addmedecin";
    }

    @PostMapping(path = "/save")
    public String saveMedecin(Model model, Medecin medecin) {
        medecinRepository.save(medecin);
        return "redirect:/medecinsList";
    }

    @GetMapping(path = "/delete")
    public String deleteMedecin(int page, int size, String search, @RequestParam(name="id") Integer id) {
        medecinRepository.deleteById(id);

        return "redirect:/medecinsList?page=" + page + "&size=" + size + "&search=" + search;

    }

    @GetMapping(path = "/details")
    public String showMedecinPatients(@RequestParam(name = "id") Integer id, Model model) {
        Medecin medecin = medecinRepository.findById(id).orElse(null);

        if (medecin == null) {
            return "redirect:/medecinsList";
        }


        model.addAttribute("medecin", medecin);
        model.addAttribute("patients", medecin.getPatient());

        return "medecinDetails";
    }
}
