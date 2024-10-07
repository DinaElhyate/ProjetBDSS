package xml.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "evaluation")
public class Evaluation {
    private int evaluationId;
    private int utilisateurId;
    private int recetteId;
    private int note;
    private String date;

    @XmlElement(name = "evaluationId")
    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    @XmlElement(name = "utilisateurId")
    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    @XmlElement(name = "recetteId")
    public int getRecetteId() {
        return recetteId;
    }

    public void setRecetteId(int recetteId) {
        this.recetteId = recetteId;
    }

    @XmlElement(name = "note")
    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
