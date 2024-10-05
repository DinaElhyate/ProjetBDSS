package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Utilisateur;

public interface UtilisateurService {

    // Méthode pour rechercher un utilisateur par email et mot de passe
    Utilisateur findByEmailAndPassword(String email, String password);
}