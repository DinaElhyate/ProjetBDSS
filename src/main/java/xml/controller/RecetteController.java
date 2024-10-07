package xml.controller;

import org.springframework.web.bind.annotation.*;
import xml.model.Recette;
import xml.service.RecetteServiceImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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



    @DeleteMapping("/delete/{id}")
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


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRecette(@PathVariable Long id, @RequestBody Recette newRecette) {
        try {
            Recette existingRecette = recetteService.findRecetteById(id);
            if (existingRecette == null) {
                return ResponseEntity.status(404).body("Recette non trouvée.");
            }

            // Mettez à jour les champs nécessaires
            existingRecette.setTitre(newRecette.getTitre());
            existingRecette.setDescription(newRecette.getDescription());
            existingRecette.setDureePreparation(newRecette.getDureePreparation());
            existingRecette.setDureeCuisson(newRecette.getDureeCuisson());
            existingRecette.setDifficulte(newRecette.getDifficulte());
            existingRecette.setTypeCuisine(newRecette.getTypeCuisine());
            existingRecette.setDatePub(newRecette.getDatePub());
            existingRecette.setImage(newRecette.getImage());
            existingRecette.setUserId(newRecette.getUserId());

            // Appel à la méthode de mise à jour
            recetteService.updateRecette(id, existingRecette);
            return ResponseEntity.ok("Recette mise à jour avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour de la recette.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recette> getRecetteById(@PathVariable Long id) {
        Recette recette = recetteService.findRecetteById(id);
        if (recette != null) {
            return ResponseEntity.ok(recette); // Retourne la recette avec un code 200
        } else {
            return ResponseEntity.notFound().build(); // Retourne un code 404 si la recette n'est pas trouvée
        }
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<Recette> getRecetteDetails(@PathVariable Long id) {
        Recette recette = recetteService.findRecetteById(id);
        if (recette != null) {
            return ResponseEntity.ok(recette); // Retourne les détails de la recette
        } else {
            return ResponseEntity.notFound().build(); // Retourne un code 404 si la recette n'est pas trouvée
        }
    }

}
