package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Recette;

import java.util.List;

public interface RecetteService {
    List<Recette> getAllRecettes() throws Exception;
    void deleteRecette(Long id);
    void addeRecette(Recette recette);
    Recette findRecetteById(Long id);
    void addRecetteupdate(Recette recette);
}
