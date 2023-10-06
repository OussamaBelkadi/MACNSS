package ma.yc.service;

import ma.yc.dto.DossierDto;
import ma.yc.dto.PatientDto;
import ma.yc.model.Patient;

import java.util.List;

public interface PatientService {
        //+ authentification(): boolean
        //+ consulterDossier(): List
        //+ consulterDossierMatricule(): List

        boolean authentification(PatientDto patientDto);
        List<DossierDto> consulterDossiers(String Matricule);
       DossierDto consulterDossierParCode(String CodeDossier);
       boolean addPatient(PatientDto patientDto);
       Float addDeclaration(PatientDto patientDto);
       double calculeSalary(PatientDto patientDto);
       int calculAge(Patient patient);
       List<Patient> desplayPatient(PatientDto patientDto);

}
