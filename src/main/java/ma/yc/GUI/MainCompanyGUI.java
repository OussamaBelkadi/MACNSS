package ma.yc.GUI;

import ma.yc.core.Print;
import ma.yc.core.SessionManager;
import ma.yc.dto.CompanyDto;
import ma.yc.dto.PatientDto;
import ma.yc.dto.ReportSalaryDto;
import ma.yc.model.Company;
import ma.yc.model.Patient;
import ma.yc.service.CompanyService;
import ma.yc.service.PatientService;
import ma.yc.service.ReportSalaryService;
import ma.yc.service.impl.CompanyServiceImp;
import ma.yc.service.impl.PatientServiceImpl;
import ma.yc.service.impl.ReportSalaryServiceImp;
import org.springframework.cglib.core.Local;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class MainCompanyGUI implements DisplayGUI {

    private HashMap<Integer, Runnable> companyUi = new HashMap<>();
    private HashMap<Integer, Runnable> companyDashbord = new HashMap<>();

    private ReportSalaryDto reportSalaryDto;
    private ReportSalaryService reportSalaryService;
    private CompanyService companyServiceImp;
    private CompanyDto companyDto;
    private PatientDto patientDto;
    private SessionManager sessionManager;
    private PatientService patientService;



    public MainCompanyGUI(){
        this.patientService = new PatientServiceImpl();
        this.companyServiceImp = new CompanyServiceImp();
        this.reportSalaryService = new ReportSalaryServiceImp();
        this.companyDto = new CompanyDto();
        this.patientDto = new PatientDto();
        this.reportSalaryDto = new ReportSalaryDto();
        this.sessionManager = new SessionManager();
        Scanner scanner = new Scanner(System.in);

        companyUi.put(1, () -> this.authentifier(scanner));
        companyUi.put(2, () -> this.regester(scanner));
        companyDashbord.put(1, () -> this.addPatient(scanner));
        companyDashbord.put(2, () -> this.declaration(scanner));
    }
    private String companyId  = null;

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString(int length) {

        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
        public static LocalDate getDate(String s, DateTimeFormatter format){
            LocalDate date = LocalDate.parse(s,format);
            return date;
        }

    public int dashbordSociete(Scanner scanner ) {

        Print.log("Bienvenue dans l'application de gestion des patients");
        Print.log("=== Societe XXXX  ===");
        Print.log("1 - Ajouter Collaborateur ");
        Print.log("2 - Declaration ");
        Print.log("0 - Exit ");
        Print.log("---------------------------------------------------");
        System.out.print("Enter the choce here: ");
        int choice = scanner.nextInt();
        Runnable selectedAction = this.companyDashbord.get(choice);
        if (selectedAction != null){
            selectedAction.run();
        }
        return 0;
    }

    private void authentifier(Scanner scanner){
        boolean result = false;
        Print.log("Bienvenue dans l'application de gestion des patients");

        Print.log("=== Enter votre donnez pour se connecter avec votre interface  ===");
        Print.log("Entrer la nomination de la societe :");
        String nomination = scanner.nextLine();
        companyDto.nomination = nomination;
        Print.log("Entrer l'identifient de la societe :");
        String identifiant = scanner.nextLine();
        companyDto.identifiant = identifiant;
        companyId = identifiant;
        boolean resultCheck = this.companyServiceImp.checkExisting(companyDto);
        if (resultCheck){
            this.dashbordSociete(scanner);
        }else {
            Print.log("Tu remprent l'oppiration!");
        }
    }
    private void addPatient(Scanner scanner) {
        scanner.nextLine();
        String identiantSociete = sessionManager.getSession("idCompany");
        boolean result = false;
        Print.log("Bienvenue dans l'application de gestion des patients");

        Print.log("=== Vous voulez ajouter un patient  ===");
        Print.log("Entrer le nom du patient :");
        String name = scanner.nextLine();
        patientDto.nom = name;
        patientDto.idCompany = identiantSociete;
        Print.log("Entrer la date de naissence :");
        String birthday = scanner.nextLine();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = getDate(birthday,format);
        patientDto.birthday = date;
        Print.log("Entrer le matricule du patient :");
        String matricule = scanner.nextLine();
        patientDto.matricule = matricule;

        Patient patient = new Patient();
        patient.setMatricule(matricule);
        reportSalaryDto.patient = patient;
        Company company = new Company();
        company.setIdCompany(companyId);
        patientDto.company = company;

        Print.log("Entrer le salaire du patient :");
        Float salary = scanner.nextFloat();
        patientDto.Salaire = salary;
        reportSalaryDto.salaire = salary;
        Print.log("Entrer la cotisation:");
        int cotisation = scanner.nextInt();
        patientDto.cotisationS = cotisation;
        Print.log("Entrer le nombre de jour de travail :");
        Integer workingDay = scanner.nextInt();
        patientDto.Numberwd = workingDay;
        reportSalaryDto.jourTravail = workingDay;
        result = patientService.addPatient(patientDto);
        if(result){

            reportSalaryService.addSalary(reportSalaryDto);

        }
        System.out.println(result?"Success register":"Register rejected");
    }

    public void declaration(Scanner scanner){
        float result = 0;
        scanner.nextLine();
        Print.log("=== Enter votre donnez pour se connecter avec votre interface  ===");
        Print.log("Entrer le matricule du patient :");
        String matricule = scanner.nextLine();
        Patient patient = new Patient();
        patient.setMatricule(matricule);
        this.patientDto.matricule = matricule;
        this.reportSalaryDto.patient = patient;
        Print.log("Entrer les nombres des jour de travail");
        int nbrJour = scanner.nextInt();
        this.patientDto.Numberwd = nbrJour;
        this.reportSalaryDto.jourTravail = nbrJour;

        result = this.patientService.addDeclaration(patientDto);
        if (result != 0){
            reportSalaryDto.salaire = result;
            reportSalaryService.addSalary(reportSalaryDto);
            System.out.println("Declaration complet");
        }else {
            System.out.println("problemme provient ");
        }
    }

    @Override
    public int displayMainOptions(Scanner scanner) {
        Print.log("Bienvenue dans l'application de gestion des patients");
        Print.log("=== OPERATION  ===");
        Print.log("1 - Authentification ");
        Print.log("2 - Regester ");
        Print.log("0 - Exit ");
        Print.log("---------------------------------------------------");
        System.out.print("Enter the choce here: ");
        int choice = scanner.nextInt();
        Runnable selectedAction = this.companyUi.get(choice);
        if (selectedAction != null){
            selectedAction.run();
        }
        return 0;
    }

    public void regester(Scanner scanner){
        Print.log("Enregistrer la societe ");
        Print.log("Entrer la nomination de la societe");
        String nomination = scanner.nextLine();
        companyDto.nomination = nomination;
        companyDto.identifiant = generateRandomString(6);
        boolean result = companyServiceImp.addCompany(companyDto);
        System.out.println(result?"Success Registration":"Shut Registration");
    }
}
