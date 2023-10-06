package ma.yc.GUI;

import ma.yc.core.Print;
import ma.yc.service.impl.DossierServiceImpl;

import java.util.HashMap;
import java.util.Scanner;

public class MainGUI  implements  DisplayGUI{


    private DossierServiceImpl dossierService = new DossierServiceImpl();

    private HashMap<Integer , DisplayGUI> options = new HashMap<>();

    public MainGUI() {
        this.options.put(1, new MainAdministeurGUI());
        this.options.put(2, new MainAgentGUI());
        this.options.put(3, new MainPaitentGUI());
        this.options.put(4, new MainCompanyGUI());
    }


    @Override
    public int displayMainOptions(Scanner scanner)  {
        Print.log("=== OPERATION  ===");
        Print.log("1 - are you an admin ? ");
        Print.log("2 - are you an agent ? ");
        Print.log("3 - are you a patient ? ");
        Print.log("4 - are you a company ? ");

        Print.log("0 - Exit ");

        int choice = scanner.nextInt();
        if (choice == 0 || choice > 4){
            Print.log("your choice is not correct");
            System.exit(0);
            return 0;
        }
        DisplayGUI displayGUI = this.options.get(choice);
        displayGUI.displayMainOptions(scanner);

        return choice;
    }
}
