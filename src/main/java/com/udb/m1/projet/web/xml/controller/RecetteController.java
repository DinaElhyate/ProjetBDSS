package com.udb.m1.projet.web.xml.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.udb.m1.projet.web.xml.model.Recette;
import com.udb.m1.projet.web.xml.service.RecetteServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/recettes")
public class RecetteController {

    private final RecetteServiceImpl recetteService;

    public RecetteController(RecetteServiceImpl recetteService) {
        this.recetteService = recetteService;
    }

    // Méthode pour afficher toutes les recettes
    @GetMapping
    public String index(Model model) {
        List<Recette> recettes = recetteService.getAllRecettes();
        System.out.println("Recettes récupérées : " + recettes);

        if (recettes.isEmpty()) {
            System.out.println("RecettesERROUR: " + recettes);
            return "recette/error"; // Rediriger vers la page d'erreur si aucune recette n'est trouvée
        }

        // Debugging pour vérifier les recettes
        System.out.println("Recettes: " + recettes);

        model.addAttribute("recettes", recettes);
        return "recette/resultats"; // Retourne la vue avec les recettes
    }

    // Méthode pour supprimer une recette
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            recetteService.deleteRecette(id);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de la suppression de la classe");
        }
        return "recette/suppression";
    }
////ajouter une recette
    @PostMapping("/add")
    public String addRecette(@RequestBody Recette recette, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Recette reçue: " + recette);
            recetteService.addRecette(recette); // Appelle la méthode de service pour ajouter la recette
            redirectAttributes.addFlashAttribute("successMessage", "Recette ajoutée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout de la recette.");
        }
        return "redirect:/recettes"; // Redirige vers la liste des recettes ou la page d'accueil
    }

//// la modification des champs
@PostMapping("/update/{id}")
public String updateRecette(@PathVariable Long id, @RequestBody Recette newRecette, RedirectAttributes redirectAttributes) {
    try {
        // Récupérer la recette existante par son ID
        Recette existingRecette = recetteService.findRecetteById(id);
        if (existingRecette == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Recette non trouvée.");
            return "redirect:/recettes"; // Redirection si la recette n'existe pas reccettes est une page html qui existe dans templates
        }

        // Mettre à jour les champs de la recette existante avec ceux de la nouvelle recette
        existingRecette.setTitre(newRecette.getTitre());
        existingRecette.setDescription(newRecette.getDescription());
        existingRecette.setDureePreparation(newRecette.getDureePreparation());
        existingRecette.setDureeCuisson(newRecette.getDureeCuisson());
        existingRecette.setDifficulte(newRecette.getDifficulte());
        existingRecette.setTypeCuisine(newRecette.getTypeCuisine());
        existingRecette.setDatePub(newRecette.getDatePub());


        // Ajouter ou sauvegarder la nouvelle recette mise à jour (avec le même ID)
        recetteService.addRecetteupdate(existingRecette);

        // Supprimer l'ancienne recette (optionnel si vous souhaitez supprimer explicitement)
        recetteService.deleteRecette(id);

        redirectAttributes.addFlashAttribute("successMessage", "Recette mise à jour avec succès !");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de la recette.");
        e.printStackTrace();
    }

    return "redirect:/recettes";
}








}
