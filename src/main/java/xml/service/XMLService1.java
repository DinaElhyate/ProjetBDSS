package com.udb.m1.projet.web.xml.service;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.udb.m1.projet.web.xml.model.Recettes;
import com.udb.m1.projet.web.xml.model.Utilisateurs;

@Service
public class XMLService1 {

    private static final String FILE_PATH = "./recette.xml";
    private static final Logger logger = LoggerFactory.getLogger(XMLService1.class);

    // Method to save the 'Recettes' object into an XML file
    public void save(Recettes recettes) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Recettes.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(recettes, new File(FILE_PATH)); // Save the data to XML
        } catch (JAXBException e) {
            logger.error("Error saving data to XML file", e);
            throw new RuntimeException("Error saving data to XML file", e);
        }
    }

    // Private method to create an XML file if not present
    private void createXMLFile() {
        try {
            Recettes newRecettes = new Recettes(); // Create a new empty Recettes object
            JAXBContext jaxbContext = JAXBContext.newInstance(Recettes.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Format XML

            File file = new File(FILE_PATH);
            marshaller.marshal(newRecettes, file); // Create and write to the XML file
            logger.info("New XML file created successfully.");
        } catch (JAXBException e) {
            logger.error("Error creating XML file", e);
            throw new RuntimeException("Error creating XML file", e);
        }
    }

    // Method to load data from XML into a 'Recettes' object
    public Recettes load() {
        try {
            File file = new File(FILE_PATH);

            // Check if the XML file exists
            if (!file.exists()) {
                logger.info("recette.xml file not found. Creating a new file...");
                createXMLFile(); // Create a new file if necessary
                return new Recettes(); // Return an empty Recettes object
            }

            // Initialize JAXB context for the 'Recettes' class
            JAXBContext jaxbContext = JAXBContext.newInstance(Recettes.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Deserialize (unmarshal) the XML file into a 'Recettes' object
            Recettes recettes = (Recettes) unmarshaller.unmarshal(file);

            // Check if the 'Recettes' object or its contents are null
            if (recettes == null || recettes.getRecettes() == null) {
                logger.warn("No recettes found in the 'Recettes' object!");
                return new Recettes(); // Return an empty Recettes object
            }

            // Return the 'Recettes' object loaded from the XML file
            logger.info("Data successfully loaded from the XML file.");
            return recettes;

        } catch (JAXBException e) {
            logger.error("JAXB error while loading data from XML file", e);
            throw new RuntimeException("Error loading data from XML file", e);
        }
    }



    public Utilisateurs loadUtilisateurs() { // Changement de 'loadUtilisateur' à 'loadUtilisateurs'
        try {
            File file = new File(FILE_PATH); // Utiliser la constante définie pour le chemin du fichier

            // Vérifier si le fichier XML existe
            if (!file.exists()) {
                logger.info("Le fichier recette.xml n'a pas été trouvé. Création d'un nouveau fichier...");
                createXMLFile(); // Créez un nouveau fichier si nécessaire
                return new Utilisateurs(); // Retourner un objet Utilisateurs vide
            }

            // Initialiser le contexte JAXB pour la classe 'Utilisateurs'
            JAXBContext jaxbContext = JAXBContext.newInstance(Utilisateurs.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Désérialiser (unmarshal) le fichier XML en un objet 'Utilisateurs'
            Utilisateurs utilisateurs = (Utilisateurs) unmarshaller.unmarshal(file);

            // Vérifier si l'objet 'Utilisateurs' ou son contenu est null
            if (utilisateurs == null || utilisateurs.getUtilisateurs() == null) {
                logger.warn("Aucun utilisateur trouvé dans l'objet 'Utilisateurs' !");
                return new Utilisateurs(); // Retourner un objet Utilisateurs vide
            }

            // Retourner l'objet 'Utilisateurs' chargé depuis le fichier XML
            logger.info("Les données ont été chargées avec succès depuis le fichier XML.");
            return utilisateurs;

        } catch (JAXBException e) {
            logger.error("Erreur JAXB lors du chargement des données depuis le fichier XML", e);
            throw new RuntimeException("Erreur lors du chargement des données depuis le fichier XML", e);
        }
}
}