package com.udb.m1.projet.web.xml.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.udb.m1.projet.web.xml.model.Recette;
import com.udb.m1.projet.web.xml.service.RecetteServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recettes")
public class RecetteController {

    private final RecetteServiceImpl recetteService;

    public RecetteController(RecetteServiceImpl recetteService) {
        this.recetteService = recetteService;
    }


    @GetMapping
    public ResponseEntity<List<Recette>> index() {
        List<Recette> recettes = recetteService.getAllRecettes();

        if (recettes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(recettes);
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            recetteService.deleteRecette(id);
            return ResponseEntity.ok("Recette supprimée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la suppression de la recette.");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRecette(@RequestBody Recette recette) {
        try {
            recetteService.addRecette(recette);
            return ResponseEntity.status(201).body("Recette ajoutée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'ajout de la recette.");
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateRecette(@PathVariable Long id, @RequestBody Recette newRecette) {
        try {
            Recette existingRecette = recetteService.findRecetteById(id);
            if (existingRecette == null) {
                return ResponseEntity.status(404).body("Recette non trouvée.");
            }


            existingRecette.setTitre(newRecette.getTitre());
            existingRecette.setDescription(newRecette.getDescription());
            existingRecette.setDureePreparation(newRecette.getDureePreparation());
            existingRecette.setDureeCuisson(newRecette.getDureeCuisson());
            existingRecette.setDifficulte(newRecette.getDifficulte());
            existingRecette.setTypeCuisine(newRecette.getTypeCuisine());
            existingRecette.setDatePub(newRecette.getDatePub());
            existingRecette.setImage(newRecette.getImage());
            existingRecette.setUserId(newRecette.getUserId());

            recetteService.addRecetteupdate(existingRecette);
            return ResponseEntity.ok("Recette mise à jour avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour de la recette.");
        }
    }
}
