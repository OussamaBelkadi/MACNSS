package ma.yc.model;

import java.time.LocalDate;
import java.util.List;

public class Patient {
    private String matricule;
    private int Numberwd ;
    private Float Salaire;

    private int cotisationS;
    private String statusRetraitment;
    private Float SalaireRetrait;
    private LocalDate birthday;
    private List<Dossier> dossiers;
    private String nom;

    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getNumberwd() {
        return Numberwd;
    }

    public void setNumberwd(int numberwd) {
        Numberwd = numberwd;
    }

    public Float getSalaire() {
        return Salaire;
    }

    public void setSalaire(Float salaire) {
        Salaire = salaire;
    }

    public int getCotisationS() {
        return cotisationS;
    }

    public void setCotisationS(int cotisationS) {
        this.cotisationS = cotisationS;
    }

    public String getStatusRetraitment() {
        return statusRetraitment;
    }

    public void setStatusRetraitment(String statusRetraitment) {
        this.statusRetraitment = statusRetraitment;
    }

    public Float getSalaireRetrait() {
        return SalaireRetrait;
    }

    public void setSalaireRetrait(Float salaireRetrait) {
        SalaireRetrait = salaireRetrait;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public String getMatricule() {
        return matricule;
    }

    public List<Dossier> getDossiers() {
        return dossiers;
    }

    public void setDossiers(List<Dossier> dossiers) {
        this.dossiers = dossiers;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
