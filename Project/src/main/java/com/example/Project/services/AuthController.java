package com.example.Project.services;

import com.example.Project.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.Project.services.UserService;

@Controller
@RequestMapping("")
public class AuthController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }



    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        //Récupère la session HTTP en cours via la requête.
        session.invalidate();
        //  détruit la session en supprimant ainsi toutes les données utilisateur associées.
        SecurityContext securityContext = SecurityContextHolder.getContext();
        // SecurityContextHolder est utilisé par Spring Security pour
        // stocker les informations de l'utilisateur authentifié.
        securityContext.setAuthentication(null);
        //En définissant l'authentification à null, l'utilisateur est explicitement déconnecté.
        return "redirect:/login";
        //Une redirection HTTP est effectuée vers la page de connexion (/login).
    }


    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response, Model model){
        User user = new User();
        // Un nouvel objet utilisateur vide est instancié.
        // Cet objet sera lié au formulaire d'inscription.
        model.addAttribute("user",user);
        //L'objet user est ajouté au modèle pour qu'il puisse être utilisé dans la vue
        return "register";
    }




    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String createNewUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user")User user){
        // HttpServletRequest request: Objet permettant d'accéder à la requête HTTP.
        // HttpServletResponse response: Objet permettant de manipuler la réponse HTTP.
        // @ModelAttribute("user") User user) : L'objet utilisateur lié au formulaire d'inscription.

        try {
            // Définit le rôle de l'utilisateur en tant que "USER" par défaut.
            user.setRole("USER");
            //  sauvegarder le nouvel utilisateur dans la base de données.
            User newUser = userService.createUser(user);
            // Si l'utilisateur n'a pas pu être créé, redirige vers la page d'inscription avec un message d'erreur.
            if(newUser == null){
                return "redirect:/register?error";
            }

            // Authentifie automatiquement le nouvel utilisateur après sa création.
            // Crée un token d'authentification avec l'email et le mot de passe fournis.
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
            // Met à jour le contexte de sécurité avec l'authentification de l'utilisateur.
            SecurityContext securityContext = SecurityContextHolder.getContext();
            // Crée une nouvelle session HTTP et y associe le contexte de sécurité pour persister l'authentification.
            securityContext.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,securityContext);

            // Redirige l'utilisateur vers la page d'accueil après une inscription réussie.
            return "redirect:/menu";

        } catch (Exception e){
            //En cas d'erreur (par exemple, si l'email est déjà utilisé), redirige vers la page d'inscription avec une indication d'erreur.
            return "redirect:/register?error";
        }

    }

}
