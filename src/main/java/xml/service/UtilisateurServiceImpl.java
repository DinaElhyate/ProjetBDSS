package xml.service;

import xml.model.Utilisateur;
import xml.model.Utilisateurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurServiceImpl {

    private final XMLService1 xmlService;
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);
    private static final String XML_FILE_PATH = "./recette.xml";

    @Autowired
    public UtilisateurServiceImpl(xml.service.XMLService1 xmlService) {
        this.xmlService = xmlService;
    }

    // Cette méthode doit retourner une liste d'Utilisateur
    public List<Utilisateur> loadUtilisateurs() {
        logger.info("Chargement des utilisateurs...");

        Utilisateurs utilisateursContainer = xmlService.loadUtilisateurs(); // Chargez l'objet Utilisateurs
        if (utilisateursContainer == null) {
            logger.warn("L'objet Utilisateurs est null !");
            return new ArrayList<>(); // Retourne une liste vide si aucun utilisateur n'est trouvé
        }

        if (utilisateursContainer.getUtilisateurs() == null || utilisateursContainer.getUtilisateurs().isEmpty()) {
            logger.warn("Aucun utilisateur trouvé dans l'objet Utilisateurs !");
            return new ArrayList<>(); // Retourne une liste vide si aucun utilisateur n'est trouvé
        }

        logger.info("Utilisateurs récupérés : " + utilisateursContainer.getUtilisateurs().size() + " utilisateurs trouvés.");
        return utilisateursContainer.getUtilisateurs(); // Retourne la liste des utilisateurs
    }

    // Méthode pour rechercher un utilisateur par email et mot de passe
    public Utilisateur findByEmailAndPassword(String email, String password) {
        List<Utilisateur> utilisateurs = loadUtilisateurs(); // Récupérer la liste des utilisateurs

        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getEmail().equals(email) && utilisateur.getPassword().equals(password)) {
                logger.info("Utilisateur trouvé : " + utilisateur);
                return utilisateur; // Retourner l'utilisateur trouvé
            }
        }

        logger.warn("Aucun utilisateur trouvé avec cet email et ce mot de passe.");
        return null; // Retourner null si aucun utilisateur n'est trouvé
    }
}
