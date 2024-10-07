package xml.model;
import java.util.List;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "donnees")
public class Utilisateurs {

    private List<Utilisateur> utilisateurs;

    // Annotations pour la liste de utilisateurs
    @XmlElementWrapper(name = "utilisateurs") // Correspond à l'élément parent <recettes>
    @XmlElement(name = "utilisateur") // Correspond à chaque élément <recette> à l'intérieur de <recettes>
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
