package xml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "recette")
public class Recette {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("titre")
    private String titre;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dureePreparation")
    private String dureePreparation;

    @JsonProperty("dureeCuisson")
    private String dureeCuisson;

    @JsonProperty("difficulte")
    private String difficulte;

    @JsonProperty("typeCuisine")
    private String typeCuisine;

    @JsonProperty("datePub")
    private String datePub;

    @JsonProperty("image")
        private String  image;

      @JsonProperty("userId")
            private Long userId;

    // Getters et Setters
    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }



  @XmlElement(name = "image")
    public String getImage() {
        return image;
    }

    // Setter for image
    public void setImage(String image) {
        this.image = image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "titre")
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "dureePreparation")
    public String getDureePreparation() {
        return dureePreparation;
    }

    public void setDureePreparation(String dureePreparation) {
        this.dureePreparation = dureePreparation;
    }

    @XmlElement(name = "dureeCuisson")
    public String getDureeCuisson() {
        return dureeCuisson;
    }

    public void setDureeCuisson(String dureeCuisson) {
        this.dureeCuisson = dureeCuisson;
    }

    @XmlElement(name = "difficulte")
    public String getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
    }

    @XmlElement(name = "typeCuisine")
    public String getTypeCuisine() {
        return typeCuisine;
    }

    public void setTypeCuisine(String typeCuisine) {
        this.typeCuisine = typeCuisine;
    }

    @XmlElement(name = "datePub")
    public String getDatePub() {
        return datePub;
    }

    public void setDatePub(String datePub) {
        this.datePub = datePub;
    }

       @XmlElement(name = "userId")
        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

    @Override

    public String toString() {
        return "Recette{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", dureePreparation='" + dureePreparation + '\'' +
                ", dureeCuisson='" + dureeCuisson + '\'' +
                ", difficulte='" + difficulte + '\'' +
                ", typeCuisine='" + typeCuisine + '\'' +
                ", datePub='" + datePub + '\'' +
                ", image='" + image + '\'' + // Include image
                ", userId=" + userId + // Include userId
                '}';
    }

}
