package com.udb.m1.projet.web.xml.service;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.udb.m1.projet.web.xml.model.Rec;

@Service
public class XMLService1 {

    private static final String FILE_PATH = "C:\\Users\\amalo\\Downloads\\projet-web-xml-main\\projet-web-xml-main\\recette.xml";
    private static final Logger logger = LoggerFactory.getLogger(XMLService1.class);

    public void save(Rec rec) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Rec.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(rec, new File(FILE_PATH));
        } catch (JAXBException e) {
            logger.error("Erreur lors de l'enregistrement des données dans le fichier XML", e);
            throw new RuntimeException("Erreur lors de l'enregistrement des données dans le fichier XML", e);
        }
    }

    private void createXMLFile() {
        try {
            Rec newRec = new Rec(); // Crée un nouvel objet Rec vide ou initialisé
            JAXBContext jaxbContext = JAXBContext.newInstance(Rec.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Formatage du XML

            File file = new File(FILE_PATH);
            marshaller.marshal(newRec, file); // Créer et écrire dans le fichier XML
            logger.info("Nouveau fichier XML créé avec succès.");
        } catch (JAXBException e) {
            logger.error("Erreur lors de la création du fichier XML", e);
            throw new RuntimeException("Erreur lors de la création du fichier XML", e);
        }
    }

    public Rec load() {
        try {
            File file = new File(FILE_PATH);

            // Vérifier si le fichier XML existe
            if (!file.exists()) {
                logger.info("Fichier recette.xml introuvable. Création d'un nouveau fichier...");
                createXMLFile(); // Créer un nouveau fichier si nécessaire
                return new Rec(); // Retourne un objet Rec vide
            }

            // Initialiser le contexte JAXB pour la classe Rec
            JAXBContext jaxbContext = JAXBContext.newInstance(Rec.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Désérialiser (unmarshal) le fichier XML en un objet Rec
            Rec rec = (Rec) unmarshaller.unmarshal(file);

            // Vérifier si l'objet Rec ou ses recettes sont null
            if (rec == null || rec.getRecettes() == null) {
                logger.warn("Aucune recette trouvée dans l'objet Rec !");
                return new Rec(); // Retourne un objet Rec vide
            }

            // Retourner l'objet Rec chargé depuis le fichier XML
            logger.info("Données chargées avec succès depuis le fichier XML.");
            return rec;

        } catch (JAXBException e) { // Seule cette exception est pertinente
            logger.error("Erreur JAXB lors du chargement des données depuis le fichier XML", e);
            throw new RuntimeException("Erreur lors du chargement des données depuis le fichier XML", e);
        }
    }

}
