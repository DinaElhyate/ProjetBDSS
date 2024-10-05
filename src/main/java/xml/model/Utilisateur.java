package com.udb.m1.projet.web.xml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "utilisateur") // Indique que c'est un élément XML racine
public class Utilisateur {

    @JsonProperty("id") // Utilisé pour la sérialisation JSON
    private Long id;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("niveauDeSpecialite")
    private String niveauDeSpecialite;

    @JsonProperty("password") // Pour la sérialisation JSON
    private String password; // Champ pour le mot de passe

    // Getters et Setters avec annotations XML
    @XmlElement(name = "id") // Spécifie l'élément XML <id>
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "fullName") // Spécifie l'élément XML <fullName>
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @XmlElement(name = "email") // Spécifie l'élément XML <email>
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name = "niveauDeSpecialite") // Spécifie l'élément XML <niveauDeSpecialite>
    public String getNiveauDeSpecialite() {
        return niveauDeSpecialite;
    }

    public void setNiveauDeSpecialite(String niveauDeSpecialite) {
        this.niveauDeSpecialite = niveauDeSpecialite;
    }

    @XmlElement(name = "password") // Spécifie l'élément XML <password>
    public String getPassword() { // Ajout de la méthode getPassword
        return password;
    }

    public void setPassword(String password) { // Ajout de la méthode setPassword
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", niveauDeSpecialite='" + niveauDeSpecialite + '\'' +
                ", password='" + password + '\'' + // Inclusion du mot de passe dans toString
                '}';
    }
}
