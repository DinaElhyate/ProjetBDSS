package xml.controller;

import xml.model.Utilisateur;
import xml.service.UtilisateurServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurServiceImpl utilisateurService;

    public UtilisateurController(UtilisateurServiceImpl utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
            Utilisateur utilisateur = utilisateurService.findByEmailAndPassword(email, password);

            if (utilisateur != null) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Connexion réussie"));
            } else {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Email ou mot de passe incorrect."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Une erreur s'est produite lors de la connexion."));
        }
    }
}
