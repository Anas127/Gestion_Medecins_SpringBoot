    package com.example.Project.services;

    import com.example.Project.entities.User;
    import com.example.Project.repositories.UserRepository;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    import java.util.Arrays;
    import java.util.List;

    @Service
    public class CustomUserDetailsService implements UserDetailsService {
        private UserRepository userRepository;

        // Constructeur pour injecter le UserRepository dans cette classe.
        public CustomUserDetailsService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        // Surcharge de la méthode loadUserByUsername définie par l'interface UserDetailsService.
        // Cette méthode est utilisée par Spring Security pour récupérer les détails d'un utilisateur donné (via son email ici).
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            // Requête à la base de données pour récupérer un utilisateur via son email.
            User user = userRepository.findUserByEmail(email);

            // Si l'utilisateur n'existe pas, on lance une exception UsernameNotFoundException.
            if (user == null) {
                throw new UsernameNotFoundException("No user found with email");
            }

            // Récupère les rôles de l'utilisateur sous forme de liste (ici, un seul rôle est utilisé pour simplifier).
            List<String> roles = Arrays.asList(user.getRole());

            // Création d'un objet UserDetails à partir des informations de l'utilisateur.
            // UserDetails est une classe de Spring Security qui contient les informations nécessaires pour l'authentification.
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(user.getEmail())    // Définit l'email comme nom d'utilisateur.
                            .password(user.getPassword()) // Associe le mot de passe de l'utilisateur.
                            .roles("USER")               // Définit le rôle de l'utilisateur comme "USER".
                            .build();

            // Retourne l'objet UserDetails à Spring Security pour l'utiliser lors de l'authentification.
            return userDetails;
        }



    }



