package ma.yc.GUI;

import com.sun.tools.javac.Main;
import ma.yc.core.Print;
import ma.yc.core.Util;
import ma.yc.dao.PatientDao;
import ma.yc.dto.DossierDto;
import ma.yc.dto.PatientDto;
import ma.yc.model.Patient;
import ma.yc.service.PatientService;
import ma.yc.service.impl.PatientServiceImpl;

import java.security.PublicKey;
import java.util.List;
import java.util.Scanner;

public class MainPaitentGUI implements DisplayGUI{

    private PatientService patientService;
    private PatientDto patientDto ;

    public MainPaitentGUI() {
        this.patientService = new PatientServiceImpl();
        this.patientDto = new PatientDto();
    }

    @Override
    public int displayMainOptions(Scanner scanner) {
//       this.patientAuth(scanner);
       this.patientDashboard(scanner);
        return 0;
    }

    public int patientAuth(Scanner scanner){
        Print.log("Bienvenue dans l'application de gestion des patients");
        Print.log("Authentification");
        String matricule = Util.readString("Matricule",scanner);
        PatientDto patientDto = new PatientDto();
        patientDto.matricule = matricule;
        boolean isAuth = this.patientService.authentification(patientDto);
        if (isAuth){
            patientDto.matricule = matricule;
            Print.log("Authentification success ✅");
            this.patientDashboard(scanner);
        }

        Print.log("this matricule is not correct ");
        return 0;
    }
    public boolean authentifier(PatientDto patientDto) {
        return this.patientService.authentification(patientDto);
    }

    public int patientDashboard(Scanner scanner) {
        Print.log("\t\t === OPERATION  ===");
        Print.log("\t\t 1 - Consult your dossier by num_dossier ");
        Print.log("\t\t 2 - Consult all dossiers ");
        Print.log("\t\t 3 - Go back to the main menu ");
        Print.log("\t\t 4- Voir le salaire de retraite ");
        Print.log("\t\t 0 - Exit ");
        int choice = scanner.nextInt();
        scanner = new Scanner(System.in);
        switch (choice){

            case 1:

                //consult dossier by matricule
                Print.log("Entre le code_bar de votre dossier");
                String code_bar = Util.readString("num_dossier",scanner);
                //DossierDto dossierDto = new DossierDto(code_bar, patientDto.matricule);
                DossierDto dossierDto = this.patientService.consulterDossierParCode(code_bar);
                Print.log("Folder status " + dossierDto.status.toString());
                Print.log("Total to Reimburse " + dossierDto.totalRemboursement);


                break;
            case 2:
                Print.log("Entrer ton matricule");
                String MatriculePatient = Util.readString("matricule",scanner);
               List<DossierDto> listdossierDto = this.patientService.consulterDossiers(MatriculePatient);
                for(DossierDto d : listdossierDto){
                    Print.log("--------------------num de dossier" + d.numDossier + " ----------------------");
                    Print.log( "total de remboursement pour ce dossier " + d.totalRemboursement);
                    Print.log( "le status de dossier " + d.status.toString());
                    System.out.println("-------------------------------------------------------------");
                }

                break;
            case 3:
                //go back to the main menu
                Print.log("Go back to the main menu");
                new MainGUI().displayMainOptions(scanner);
                break;
            case 4:
                this.displaySalary(scanner);
            case 0:
                //exit
                return -1;
            default:
                Print.log("Invalid choice");
                break;
        }
        return 0;

    }

    public void displaySalary(Scanner scanner){
        Print.log("veuillew entrer votre identifient:");
        String matricule = scanner.nextLine();
        patientDto.matricule = matricule;
        Double salaire = this.patientService.calculeSalary(patientDto);
        if (salaire != 0){
            List<Patient> patients = this.patientService.desplayPatient(patientDto);
            System.out.println("Nom: " + patients.get(1)+" "+ patients.get(2)+" "+ patients.get(3)+" "+patients.get(4)+" "+patients.get(5));
        }else {
            System.out.println("Vous ne pouvez pas benificier du retraite");
            List<Patient> patients = this.patientService.desplayPatient(patientDto);
            System.out.println("Nom: " + patients.get(1)+" "+ patients.get(2)+" "+ patients.get(3)+" "+patients.get(4)+" "+patients.get(5));

        }
    }

}
