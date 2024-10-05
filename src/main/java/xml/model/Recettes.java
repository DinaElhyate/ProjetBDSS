package com.udb.m1.projet.web.xml.model;
import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "donnees")
public class Recettes {

    private List<Recette> recettes;

    // Annotations pour la liste de recettes
    @XmlElementWrapper(name = "recettes") // Correspond à l'élément parent <recettes>
    @XmlElement(name = "recette") // Correspond à chaque élément <recette> à l'intérieur de <recettes>
    public List<Recette> getRecettes() {
        return recettes;
    }

    public void setRecettes(List<Recette> recettes) {
        this.recettes = recettes;
    }
}
