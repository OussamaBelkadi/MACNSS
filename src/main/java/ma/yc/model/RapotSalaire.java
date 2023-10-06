package ma.yc.model;

public class RapotSalaire {
    private Float salaire;
    private int nbrJour;
    private Patient patient;

    public Float getSalaire() {
        return salaire;
    }

    public void setSalaire(Float salaire) {
        this.salaire = salaire;
    }

    public int getNbrJour() {
        return nbrJour;
    }

    public void setNbrJour(int nbrJour) {
        this.nbrJour = nbrJour;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public static class RaportSalaireBuilder{
        private RapotSalaire rapotSalaire = new RapotSalaire();
        public RaportSalaireBuilder setSalaire(Float salaire){
            this.rapotSalaire.salaire = salaire;
            return this;
        }
        public RaportSalaireBuilder setNbrJour(int nbrJour) {
            this.rapotSalaire.nbrJour = nbrJour;
            return this;
        }

        public  RaportSalaireBuilder setPatient(Patient patient){
            this.rapotSalaire.patient = patient;
            return this;
        }
        public RapotSalaire build(){
            return rapotSalaire;
        }
    }
}
