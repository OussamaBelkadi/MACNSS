package ma.yc.service.impl;

import ma.yc.Mapper.Mapper;
import ma.yc.Mapper.impl.DossierMapper;
import ma.yc.Mapper.impl.PatientMapper;
import ma.yc.core.Print;
import ma.yc.dao.DossierDao;
import ma.yc.dao.PatientDao;
import ma.yc.dao.impl.DossierDaoImpl;
import ma.yc.dao.impl.PatientDaoImpl;
import ma.yc.dto.DossierDto;
import ma.yc.dto.PatientDto;
import ma.yc.model.Dossier;
import ma.yc.model.Patient;
import ma.yc.model.RapotSalaire;
import ma.yc.service.PatientService;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class PatientServiceImpl implements PatientService {

    private PatientDao patientDao;
    private Mapper<DossierDto,Dossier> dossierMapper;
    private DossierDao dossierDao;
    private Mapper<PatientDto, Patient> patientMapper;


    public PatientServiceImpl() {
        this.patientDao = new PatientDaoImpl();
        this.patientMapper = new PatientMapper();
        this.dossierDao = new DossierDaoImpl();
        this.dossierMapper = new DossierMapper();
    }
    Function<Float, Float> verifySalary = value -> {
        if(value > 6000){
            return 6000F;
        } else if (value < 1000) {
            return 1000F;
        }else {
            return value;
        }

    };
    @Override
    public boolean authentification(PatientDto patientDto) {
        //:verify the patientDto is not null and the matricule is not null
        if (patientDto == null || patientDto.matricule == null) {
            return false;
        }
        //: mapped the patientDto to patientEntity
        Patient patient = this.patientMapper.toEntity(patientDto);
        //: call the patientDaoImpl.authentification(patient)
        //: return the result
        return this.patientDao.authentification(patient);
    }
    @Override
    public List<DossierDto> consulterDossiers(String Matricule) {
        List<Dossier> dossier = this.dossierDao.consulterDossiers(Matricule);
        List<DossierDto> ListdossierDto = new ArrayList<DossierDto>();
        for(Dossier d : dossier){
            ListdossierDto.add(this.dossierMapper.toDto(d));
        }
        return ListdossierDto;
    }

    @Override
    public DossierDto consulterDossierParCode(String CodeDossier) {
        Dossier dossier =  this.dossierDao.consulterDossier(CodeDossier);
        DossierDto dossierDto =  this.dossierMapper.toDto(dossier);
        return  dossierDto;
    }


    @Override
    public boolean addPatient(PatientDto patientDto) {
        boolean result = false;
        Patient patient = patientMapper.toEntity(patientDto);
        result = this.patientDao.savePatient(patient);

        return result;
    }

    @Override
    public Float addDeclaration(PatientDto patientDto) {
        boolean result = false;
        Patient patient = patientMapper.toEntity(patientDto);
        result = this.patientDao.saveDeclaration(patient);
        Float salary = this.patientDao.selectSalaire(patient);
        Float salaryPurDay = salary / 26;
        System.out.println(salaryPurDay);

        return salaryPurDay;
    }
    @Override
    public int calculAge(Patient patient) {
        String dateBirthDayDb = this.patientDao.selectbirthDay(patient).toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate databaseDate = LocalDate.parse(dateBirthDayDb, formatter);
        LocalDate date = LocalDate.now();

        int yearsDifference = date.getYear()- databaseDate.getYear() ;
        return yearsDifference;
    }

    @Override
    public List<Patient> desplayPatient(PatientDto patientDto) {
        List<Patient> patients = new ArrayList<>();
        Patient patient = patientMapper.toEntity(patientDto);
        patients = this.patientDao.patientStatus(patient);

        return patients;
    }

    @Override
    public double calculeSalary(PatientDto patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);
       int age = this.calculAge(patient);
       if (age > 55) {
           float salaireRetraite = 0;
           float salaireRetraiteFinal =0;
           double quota = 0.5;
           int dayWorking = this.patientDao.seelectDayWork(patient);
           if (dayWorking >= 1320) {
               List<RapotSalaire> raportSal = this.patientDao.selecteSalary(patient);
               float totalSalary = 0;
               int numberMonth = raportSal.size();
               for (RapotSalaire report : raportSal) {
                   totalSalary = totalSalary + report.getNbrJour() * report.getSalaire();
               }
               System.out.println(totalSalary);
               float mediumSalary = totalSalary / numberMonth;
               if (dayWorking <= 3240) {
                   salaireRetraite = salaireRetraite + (float)  (mediumSalary * quota);
               }
               if (dayWorking > 3240) {
                   int resteDay = dayWorking - 3240;
                   int precentage = resteDay / 216;
                   float plusPrecentage = (float) precentage / 100;
                   float newQuota = (float) quota + plusPrecentage;
                   if (newQuota > 0.70) {
                       newQuota = (float) 0.70;
                   }
                   salaireRetraite = mediumSalary * newQuota;

               }
               salaireRetraiteFinal = verifySalary.apply(salaireRetraite);

               DecimalFormat decimalFormat = new DecimalFormat("#.##");
               float  salaireVerifier = Float.valueOf(decimalFormat.format(salaireRetraiteFinal));

               patient.setSalaireRetrait(salaireVerifier);
               this.patientDao.saveSalaryRetirement(patient);
               return salaireVerifier;
           }
       }
        return 0;
    }


}
