package com.example.Project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medecin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private double salaire;
    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Patient> patient;
    @Override
    public String toString() {
        return "Medecin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", salaire=" + salaire +
                ", patients=" + (patient != null ? patient.size() : "0") +
                '}';
    }



}
