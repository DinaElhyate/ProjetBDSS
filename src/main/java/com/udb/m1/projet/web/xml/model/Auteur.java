package com.udb.m1.projet.web.xml.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "auteur")
public class Auteur {
    private int auteurId;

    @XmlElement(name = "auteurId")
    public int getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(int auteurId) {
        this.auteurId = auteurId;
    }
}
