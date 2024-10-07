package xml.service;

import xml.model.Recette;

import java.util.List;

public interface RecetteService {
    List<Recette> getAllRecettes() throws Exception;
    void deleteRecette(Long id);
    void addeRecette(Recette recette);
    Recette findRecetteById(Long id);
    void updateRecette(Long id, Recette recette);

}
