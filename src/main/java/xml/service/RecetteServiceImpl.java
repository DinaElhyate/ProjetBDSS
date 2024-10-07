package xml.service;

import xml.model.Recette;
import xml.model.Recettes;
import xml.model.Utilisateurs;
import xml.model.Utilisateur;
import com.thoughtworks.xstream.XStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecetteServiceImpl {

    private final XMLService1 xmlService;
    private static final Logger logger = LoggerFactory.getLogger(RecetteServiceImpl.class);
    private static final String XML_FILE_PATH = "./recette.xml"; // Chemin du fichier XML

    @Autowired
    public RecetteServiceImpl(XMLService1 xmlService) {
        this.xmlService = xmlService;
    }

    // Charge toutes les recettes depuis le fichier XML
    public List<Recette> loadRecettes() {
        logger.info("Chargement des recettes...");

        Recettes recettesContainer = xmlService.load(); // Charge l'objet Recettes
        if (recettesContainer == null) {
            logger.warn("L'objet Recettes est null !");
            return new ArrayList<>(); // Retourne une liste vide si aucune recette n'est trouvée
        }

        if (recettesContainer.getRecettes() == null || recettesContainer.getRecettes().isEmpty()) {
            logger.warn("Aucune recette trouvée dans l'objet Recettes !");
            return new ArrayList<>(); // Retourne une liste vide si aucune recette n'est trouvée
        }

        logger.info("Recettes récupérées : " + recettesContainer.getRecettes().size() + " recettes trouvées.");
        return recettesContainer.getRecettes(); // Retourne la liste des recettes
    }

    // Récupère toutes les recettes
    public List<Recette> getAllRecettes() {
        return loadRecettes();
    }

    // Supprime une recette par son ID
    public void deleteRecette(Long id) {
        logger.info("Tentative de suppression de la recette avec l'ID : " + id);

        // Charger toutes les recettes à partir du fichier XML
        List<Recette> recettes = loadRecettes();

        // Trouver la recette à supprimer
        Optional<Recette> recetteToDelete = recettes.stream()
                .filter(recette -> recette.getId().equals(id))
                .findFirst();

        if (recetteToDelete.isPresent()) {
            // Supprime la recette trouvée
            recettes.remove(recetteToDelete.get());
            logger.info("Recette avec l'ID : " + id + " a été supprimée avec succès.");
            // Sauvegarde les recettes mises à jour dans le fichier XML
            sauvegarderSectionRecettes(recettes);
        } else {
            logger.warn("Aucune recette trouvée avec l'ID : " + id);
        }
    }

    // Sauvegarde les recettes mises à jour dans le fichier XML
    private void sauvegarderSectionRecettes(List<Recette> recettes) {
        File file = new File(XML_FILE_PATH);

        try {
            // Charger le document XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            // Récupérer la section <recettes>
            NodeList recetteNodes = doc.getElementsByTagName("recette");
            Node parentNode = recetteNodes.item(0).getParentNode();

            // Supprimer toutes les anciennes recettes
            while (parentNode.hasChildNodes()) {
                parentNode.removeChild(parentNode.getFirstChild());
            }

            // Ajouter les nouvelles recettes
            for (Recette recette : recettes) {
                Element recetteElement = doc.createElement("recette");

                // Créer et ajouter les éléments enfants
                Element idElement = doc.createElement("id");
                idElement.setTextContent(String.valueOf(recette.getId()));
                recetteElement.appendChild(idElement);

                Element titreElement = doc.createElement("titre");
                titreElement.setTextContent(recette.getTitre());
                recetteElement.appendChild(titreElement);

                Element descriptionElement = doc.createElement("description");
                descriptionElement.setTextContent(recette.getDescription());
                recetteElement.appendChild(descriptionElement);

                Element dureePreparationElement = doc.createElement("dureePreparation");
                dureePreparationElement.setTextContent(recette.getDureePreparation());
                recetteElement.appendChild(dureePreparationElement);

                Element dureeCuissonElement = doc.createElement("dureeCuisson");
                dureeCuissonElement.setTextContent(recette.getDureeCuisson());
                recetteElement.appendChild(dureeCuissonElement);

                Element difficulteElement = doc.createElement("difficulte");
                difficulteElement.setTextContent(recette.getDifficulte());
                recetteElement.appendChild(difficulteElement);

                Element typeCuisineElement = doc.createElement("typeCuisine");
                typeCuisineElement.setTextContent(recette.getTypeCuisine());
                recetteElement.appendChild(typeCuisineElement);

                Element datePubElement = doc.createElement("datePub");
                datePubElement.setTextContent(recette.getDatePub());
                recetteElement.appendChild(datePubElement);

                Element imageElement = doc.createElement("image");
                imageElement.setTextContent(recette.getImage());
                recetteElement.appendChild(imageElement);

                Element userIdElement = doc.createElement("userId");
                userIdElement.setTextContent(String.valueOf(recette.getUserId()));
                recetteElement.appendChild(userIdElement);

                // Ajouter la recette au noeud parent
                parentNode.appendChild(recetteElement);
            }

            // Sauvegarder les modifications dans le fichier XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            logger.info("La section des recettes a été mise à jour avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la sauvegarde des recettes.", e);
        }
    }

    // Ajouter une nouvelle recette
    public void addRecette(Recette recette) {
        // Vérifier si l'utilisateur existe
        if (!isUserIdValid(recette.getUserId())) {
            throw new IllegalArgumentException("L'ID d'utilisateur spécifié n'existe pas.");
        }

        // Charger toutes les recettes à partir du fichier XML
        List<Recette> recettes = loadRecettes();

        // Générer un nouvel ID pour la recette
        Long newId = generateNewId(recettes);
        recette.setId(newId);

        // Ajouter la nouvelle recette à la liste
        recettes.add(recette);

        // Sauvegarder la liste mise à jour des recettes dans le fichier XML
        sauvegarderSectionRecettes(recettes);
    }

    // Vérifie si l'ID utilisateur existe dans la table Utilisateurs
    private boolean isUserIdValid(Long userId) {
        List<Utilisateur> utilisateurs = loadUtilisateurs();
        logger.info("Utilisateurs : " + utilisateurs);
        return utilisateurs.stream().anyMatch(u -> u.getId().equals(userId));
    }

    // Génère un nouvel ID incrémenté
    private Long generateNewId(List<Recette> recettes) {
        return recettes.stream()
                .mapToLong(Recette::getId)
                .max()
                .orElse(0L) + 1; // Retourne le nouvel ID
    }

    // Charge tous les utilisateurs depuis le fichier XML
    public List<Utilisateur> loadUtilisateurs() {
        logger.info("Chargement des utilisateurs...");

        Utilisateurs utilisateursContainer = xmlService.loadUtilisateurs(); // Charge l'objet Utilisateurs
        logger.info("Chargement des utilisateurs depuis XML : " + utilisateursContainer);

        // Vérifie si l'objet Utilisateurs est null
        if (utilisateursContainer == null) {
            logger.warn("L'objet Utilisateurs est null !");
            return new ArrayList<>(); // Retourne une liste vide si aucun utilisateur n'est trouvé
        }

        // Vérifie si la liste d'utilisateurs est vide ou null
        List<Utilisateur> utilisateursList = utilisateursContainer.getUtilisateurs();
        if (utilisateursList == null || utilisateursList.isEmpty()) {
            logger.warn("Aucun utilisateur trouvé dans l'objet Utilisateurs !");
            return new ArrayList<>(); // Retourne une liste vide si aucun utilisateur n'est trouvé
        }

        logger.info("Utilisateurs récupérés : " + utilisateursList.size() + " utilisateurs trouvés.");
        return utilisateursList; // Retourne la liste des utilisateurs
    }

    // Recherche une recette par son ID
    public Recette findRecetteById(Long id) {
        // Charger toutes les recettes
        List<Recette> recettes = loadRecettes();

        // Chercher la recette par ID
        return recettes.stream()
                .filter(recette -> recette.getId().equals(id))
                .findFirst()
                .orElse(null); // Retourne null si aucune recette n'est trouvée
    }

 // la mise ajour d'une recette
 public void updateRecette(Long id, Recette updatedRecette) {
     List<Recette> recettes = loadRecettes();
     Optional<Recette> existingRecette = recettes.stream()
             .filter(r -> r.getId().equals(id))
             .findFirst();

     if (existingRecette.isPresent()) {
         recettes.remove(existingRecette.get());
         updatedRecette.setId(id); // Assurez-vous que l'ID de la recette mise à jour reste le même
         recettes.add(updatedRecette);
         sauvegarderSectionRecettes(recettes);
     } else {
         throw new IllegalArgumentException("Recette non trouvée avec l'ID : " + id);
     }
 }


}
