package ma.yc.dto;

import ma.yc.model.Company;

import java.time.LocalDate;

public class PatientDto {
     
    public String matricule;
    public String nom;
    public int Numberwd ;
     public Float Salaire;
     public String idCompany;
     public int cotisationS;
     public String statusRetraitment;
     public Float SalaireRetrait;
     public LocalDate birthday;
     public Company company;
}
