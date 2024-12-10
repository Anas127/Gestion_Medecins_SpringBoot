package com.example.Project;

import com.example.Project.entities.User;
import com.example.Project.repositories.MedecinRepository;
import com.example.Project.repositories.PatientRepository;
import com.example.Project.entities.Medecin;
import com.example.Project.entities.Patient;
import com.example.Project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = "com.example.Project")
public class ProjectApplication implements CommandLineRunner {

	@Autowired
	private MedecinRepository medecinRepository;
    @Autowired
    private PatientRepository patientRepository;

	@Autowired
	private UserRepository userRepository;


	@Override
	public void run(String... args) throws Exception {
		Medecin m1 = new Medecin(null, "Dr. John", 14000.00, new ArrayList<>());
		Medecin m2 = new Medecin(null, "Dr. Alice", 15000.00, new ArrayList<>());
		Medecin m3 = new Medecin(null, "Dr. Bob", 16000.00, new ArrayList<>());

		Patient p1 = new Patient(null, "John Doe", "Fievre", m1);
		Patient p2 = new Patient(null, "Bob Johnson", "Grippe", m1);
		Patient p3 = new Patient(null, "David Wilson", "Rhume", m2);
		Patient p4 = new Patient(null, "Eve Davis", "Toux", m2);
		Patient p5 = new Patient(null, "George White", "Fatigue", m3);
		Patient p6 = new Patient(null, "Hannah Black", "Mal de dos", m3);


		m1.getPatient().add(p1);
		m1.getPatient().add(p2);
		m2.getPatient().add(p3);
		m2.getPatient().add(p4);
		m3.getPatient().add(p5);
		m3.getPatient().add(p6);
		medecinRepository.save(m1);
		medecinRepository.save(m2);
		medecinRepository.save(m3);


		// CRUD

		System.out.println("---Médecins dont le salaire est entre 10000 et 20000---");
		List<Medecin> medecins = medecinRepository.findBySalaireBetween(10000, 20000);
		for (Medecin m : medecins) {
			System.out.println(m);
		}

		System.out.println("-------------------");

		System.out.println("--Médecins dont le salaire est 15000--");
		List<Medecin> md = medecinRepository.findBySalaire(15000);
		for (Medecin m : md) {
			System.out.println(m);
		}

		System.out.println("-------------------");

		System.out.println("---Patients dont la maladie est la fièvre---");
		List<Patient> pt = patientRepository.findByMaladie("Fievre");
		for (Patient p : pt) {
			System.out.println(p);
		}

		System.out.println("-------------------");

		System.out.println("---Patients dont le prénom ou nom contiennent John comme sous-string");
		List<Patient> johns = patientRepository.findByNomContains("John");
		for (Patient p : johns) {
			System.out.println(p);
		}


		System.out.println("**********************");
		userRepository.save(new User(null,"John@g.com", "123", "John","lastname", null, "admin"));

		userRepository.save(new User(null,"Marie@g.com", "123", "marie","lastname", null, "admin"));

		for(User u : userRepository.findAll()) {
			System.out.println(u);
		}


	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}
