package ma.yc.dao;

import ma.yc.model.Patient;
import ma.yc.model.RapotSalaire;

import java.util.Date;
import java.util.List;

public interface PatientDao {

    boolean authentification(Patient patient);
    boolean savePatient(Patient patient);
    boolean saveDeclaration(Patient patient);
    float selectSalaire(Patient patient);
    int seelectDayWork(Patient patient);
    Date selectbirthDay(Patient patient);
    boolean saveSalaryRetirement(Patient patient);
    List<RapotSalaire> selecteSalary(Patient patient);
    List<Patient> patientStatus(Patient patient);

}
