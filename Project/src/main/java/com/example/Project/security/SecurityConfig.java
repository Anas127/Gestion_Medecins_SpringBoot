package com.example.Project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.example.Project.services.CustomUserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Déclare une dépendance vers le service CustomUserDetailsService pour charger les détails de l'utilisateur.
    private final CustomUserDetailsService userDetailsService;

    // Constructeur permettant d'injecter CustomUserDetailsService dans cette classe de configuration.
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Déclare un bean pour AuthenticationManager, responsable de gérer le processus d'authentification.
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, NoOpPasswordEncoder noOpPasswordEncoder)
            throws Exception {
        // Récupère un objet AuthenticationManagerBuilder à partir de la configuration HttpSecurity.
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        // Configure l'AuthenticationManagerBuilder :
        // - Utilise CustomUserDetailsService pour charger les informations des utilisateurs.
        // - Utilise NoOpPasswordEncoder pour valider les mots de passe (pas recommandé pour la production).
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(noOpPasswordEncoder);

        // Construit et retourne l'objet AuthenticationManager configuré.
        return authenticationManagerBuilder.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Autorisations :
        http
                .authorizeHttpRequests() // Active la configuration des autorisations pour les requêtes HTTP.
                .requestMatchers("/login**","/logout**", "/register", "/api/**")// Définit les chemins spécifiques à configurer.
                .permitAll() // Autorise l'accès public (sans authentification) à ces chemins :
                .anyRequest().authenticated(); // Pour toute autre requête non mentionnée explicitement, une authentification est requise.

        //Authentification :
        http
                .formLogin()//Active l'authentification basée sur un formulaire.
                .loginPage("/login") //Spécifie une page de connexion personnalisée (accessible via /login).
                .defaultSuccessUrl("/menu", true);

        return http.build(); //Construit et retourne l'objet SecurityFilterChain configuré.
    }

    //Encodeur de mots de passe :
    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}
