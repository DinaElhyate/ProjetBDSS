package com.udb.m1.projet.web.xml.service;
import com.thoughtworks.xstream.XStream;
import java.io.FileWriter;  // Pour utiliser FileWriter
import java.io.IOException;  // Pour utiliser IOException
import java.util.Collections;

import com.udb.m1.projet.web.xml.model.Recette;
import com.udb.m1.projet.web.xml.model.Rec;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException; // Assurez-vous que cet import est présent
import java.io.File; // Assurez-vous que cet import est présent
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@Service
public class RecetteServiceImpl {

    private final XMLService1 xmlService;
    private static final Logger logger = LoggerFactory.getLogger(RecetteServiceImpl.class);
    private static final String XML_FILE_PATH = "C:\\Users\\amalo\\Downloads\\projet-web-xml-main\\projet-web-xml-main\\recette.xml"; // Définissez votre chemin ici

    @Autowired
    public RecetteServiceImpl(XMLService1 xmlService) {
        this.xmlService = xmlService;
    }

    // Cette méthode doit retourner une liste de Recette
    public List<Recette> loadRecettes() {
        logger.info("Chargement des recettes...");

        Rec rec = xmlService.load(); // Chargez l'objet Rec
        if (rec == null) {
            logger.warn("L'objet Rec est null !");
            return new ArrayList<>(); // Retourne une liste vide si aucun Rec n'est trouvé
        }

        if (rec.getRecettes() == null || rec.getRecettes().isEmpty()) {
            logger.warn("Aucune recette trouvée dans l'objet Rec !");
            return new ArrayList<>(); // Retourne une liste vide si aucune recette n'est trouvée
        }

        logger.info("Recettes récupérées : " + rec.getRecettes().size() + " recettes trouvées.");
        return rec.getRecettes(); // Retourne la liste des recettes
    }



    public List<Recette> getAllRecettes() {
        return loadRecettes();


    }

    public void deleteRecette(Long id) {
        logger.info("Tentative de suppression de la recette avec l'ID : " + id);

        List<Recette> recettes = loadRecettes(); // Chargez toutes les recettes

        // Vérifiez si la recette existe
        Optional<Recette> recetteToDelete = recettes.stream()
                .filter(recette -> recette.getId().equals(id)) // Assurez-vous que 'getId()' retourne l'ID de la recette
                .findFirst();

        if (recetteToDelete.isPresent()) {
            recettes.remove(recetteToDelete.get());
            logger.info("Recette avec l'ID : " + id + " a été supprimée avec succès.");
        } else {
            logger.warn("Aucune recette trouvée avec l'ID : " + id);
        }

        sauvegarderRecettes(recettes);
    }
    private void sauvegarderRecettes(List<Recette> recettes) {
        File file = new File(XML_FILE_PATH);

        if (!file.exists() || !file.canWrite()) {
            logger.error("Le fichier XML n'est pas accessible en écriture ou introuvable.");
            return;
        } else {
            logger.info("Le fichier XML est accessible en écriture.");
        }

        XStream xstream = new XStream();
        xstream.alias("recettes", Rec.class);
        xstream.alias("recette", Recette.class);

        Rec recettesWrapper = new Rec();
        recettesWrapper.setRecettes(recettes);

        try (FileWriter writer = new FileWriter(file)) {
            // Écrire la liste directement dans la balise <recettes>
            writer.write("<recettes>\n");
            for (Recette recette : recettes) {
                xstream.toXML(recette, writer);
            }
            writer.write("</recettes>\n");
            logger.info("Recettes sauvegardées avec succès dans le fichier XML.");
        } catch (IOException e) {
            logger.error("Erreur lors de la sauvegarde des recettes dans le fichier XML : ", e);
        }
    }

    public void addRecette(Recette recette) {
        List<Recette> recettes = loadRecettes(); // Chargez toutes les recettes
        // Assignez un nouvel ID à la recette si nécessaire
        recette.setId(generateNewId(recettes)); // Assurez-vous de créer une méthode pour générer un nouvel ID
        recettes.add(recette); // Ajoutez la nouvelle recette à la liste
        sauvegarderRecettes(recettes); // Sauvegardez la liste mise à jour
    }

    public void saveRecette(Recette recette) {
        List<Recette> recettes = loadRecettes(); // Charger toutes les recettes
        for (int i = 0; i < recettes.size(); i++) {
            if (recettes.get(i).getId().equals(recette.getId())) {
                recettes.set(i, recette); // Mettre à jour la recette existante
                sauvegarderRecettes(recettes); // Sauvegarder la liste mise à jour
                return;
            }
        }

        // Si la recette n'existe pas dans la liste, l'ajouter comme nouvelle recette
        recettes.add(recette);
        sauvegarderRecettes(recettes);
    }





    public Recette findRecetteById(Long id) {
        // Charger toutes les recettes
        List<Recette> recettes = loadRecettes();

        // Rechercher la recette par son ID
        for (Recette recette : recettes) {
            if (recette.getId().equals(id)) {
                return recette; // Retourner la recette si l'ID correspond
            }
        }

        // Si aucune recette avec cet ID n'est trouvée, retourner null ou lancer une exception
        return null;
    }


    private Long generateNewId(List<Recette> recettes) {
        return recettes.stream()
                .mapToLong(Recette::getId)
                .max()
                .orElse(0L) + 1; // Retourne le nouvel ID
    }



    public void addRecetteupdate(Recette recette) {
        List<Recette> recettes = loadRecettes(); // Chargez toutes les recettes
        recettes.add(recette); // Ajoutez la recette mise à jour à la liste
        sauvegarderRecettes(recettes); // Sauvegardez la liste mise à jour

    }


}