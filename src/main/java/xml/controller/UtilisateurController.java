package com.udb.m1.projet.web.xml.controller;

import com.udb.m1.projet.web.xml.model.Utilisateur;
import com.udb.m1.projet.web.xml.service.UtilisateurServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    private final UtilisateurServiceImpl utilisateurService;

    public UtilisateurController(UtilisateurServiceImpl utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // Méthode pour la connexion de l'utilisateur
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> credentials, Model model) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        try {
            // Chercher l'utilisateur par email et mot de passe
            Utilisateur utilisateur = utilisateurService.findByEmailAndPassword(email, password);

            if (utilisateur != null) {
                // Si l'utilisateur existe, ajouter les informations de l'utilisateur au modèle
                model.addAttribute("utilisateur", utilisateur);
                return "utilisateurs/dashboard"; // Redirige vers une page tableau de bord ou accueil après connexion
            } else {
                // Si les identifiants sont incorrects, ajouter un message d'erreur
                model.addAttribute("errorMessage", "Email ou mot de passe incorrect.");
                return "utilisateurs/Login"; // Rester sur la page de connexion et afficher le message d'erreur
            }
        } catch (Exception e) {
            e.printStackTrace(); // Il peut être utile de logger l'erreur pour le débogage
            model.addAttribute("errorMessage", "Une erreur s'est produite."); // Ajouter un message d'erreur
            return "utilisateurs/Login"; // Rester sur la page de connexion
        }
    }



}
