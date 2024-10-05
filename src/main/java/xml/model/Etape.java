package com.udb.m1.projet.web.xml.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "etape")
public class Etape {
    private int numeroEtape;
    private String instruction;
    private int recetteId;

    @XmlElement(name = "numeroEtape")
    public int getNumeroEtape() {
        return numeroEtape;
    }

    public void setNumeroEtape(int numeroEtape) {
        this.numeroEtape = numeroEtape;
    }

    @XmlElement(name = "instruction")
    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @XmlElement(name = "recetteId") // Ajout de la méthode pour la clé étrangère
    public int getRecetteId() {
        return recetteId;
    }

    public void setRecetteId(int recetteId) {
        this.recetteId = recetteId;
    }
}
