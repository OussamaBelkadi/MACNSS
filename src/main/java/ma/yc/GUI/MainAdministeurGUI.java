package ma.yc.GUI;


import ma.yc.core.Print;
import ma.yc.core.Util;
import ma.yc.database.DatabaseConnection;
import ma.yc.dto.AdminDto;
import ma.yc.dto.AgentDto;
import ma.yc.dto.UserDto;
import ma.yc.service.AdminService;
import ma.yc.service.AgentService;
import ma.yc.service.impl.AdminServiceImpl;
import ma.yc.service.impl.AgentServiceImpl;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;

public class MainAdministeurGUI implements DisplayGUI{
    //need AgentService which is all agent function stored
    // in like add agent delete agent and update agent we looked in
    AdminService adminService ;
    AgentService agentService ;
    boolean isAuthentificated = false;
    private  AgentDto agentDto;


    public MainAdministeurGUI() {
        this.agentService = new AgentServiceImpl();
        this.adminService = new AdminServiceImpl();
        this.agentDto = new AgentDto();
    }



    @Override
    public int displayMainOptions(Scanner scanner)  {
        Print.log("Bienvenue dans l'application de gestion des patients");
        Print.log("=== OPERATION  ===");
        Print.log("1 - Authentification ");
        Print.log("2 - Go back to the main menu ");
        Print.log("0 - Exit ");

        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                String email;
                String password ;
                email  = Util.readString("Email",scanner);
                password = Util.readString("Password",scanner);
                AdminDto adminDto = new AdminDto(email,password);
                isAuthentificated = this.adminService.authentifier(adminDto);
                if (isAuthentificated){
                    //if the authentication is successful then show the admin dashboard
                    this.AdminDashboard(scanner);
                }
                else{
                    Print.log("Authentication failed");
                    this.displayMainOptions(scanner);
                }
                break;
            case 2:
                //go back to the main menu
                new MainGUI().displayMainOptions(scanner);
                return 0;
            case 0:
                //exit
                Print.log("Bye Bye");
                return -1;
            default:
                Print.log("Invalid choice");
                break;
        }


        return 0;
    }

    public void AdminDashboard(Scanner scanner)  {


        Print.log("=== Gestion des comptes des agents CNSS   ===");
        Print.log("1- Add an agent ");
        Print.log("2- Select All agents ");
        Print.log("3- Delete an agent ");
        Print.log("4- Update an agent ");
        Print.log("5- Go back to the main menu ");

        int choice = scanner.nextInt();
        switch (choice){
            case 1 ->this.addAgent(scanner);
            case 2->
                this.selectAllAgents(scanner);
            case 3 ->
                this.deleteAgent(scanner);
            case 4->
                this.updateAgent(scanner);
            case 5->
                this.displayMainOptions(scanner);
            default ->
                Print.log("Invalid choice");
        }


    }

    private void updateAgent(Scanner scanner) {
        Print.log("=== Update an agent ===");

        Print.log("Email : ");
        String email  = Util.readString("Email",scanner);

        agentDto.email = email;
         boolean selected = this.agentService.selectAgent(agentDto);
         if (selected){
             Print.log("=== Update an agent ===");
             String emailUptodate = Util.readString("Email",scanner);

             String passWord = Util.readString("Password",scanner);

             agentDto.email = emailUptodate;
             agentDto.password = passWord;
             boolean isUpdated = this.agentService.updateAgent(agentDto,email);
             if (isUpdated){
                 System.out.println("Update seccuss");
             }
         }else {
             System.out.println("--------------***********-------------------");
             System.out.println("Problem apper in the update function");
             System.out.println("--------------***********-------------------");
         }



    }

    private void deleteAgent(Scanner scanner) {
        boolean result  = false;
        Print.log("=== Delete an agent ===");


        Print.log("Enter your email");
        String email = Util.readString("Email",scanner);
        agentDto.email = email;
        result = this.agentService.deleteAgent(agentDto);
        if (result){
            System.out.println("Delete successfully completed");
        }else {
            System.out.println("Waring");
        }

    }

    private void selectAllAgents(Scanner scanner) {
        //todo : show all agents GUI
    }

    private void addAgent(Scanner scanner) {

        Print.log("Bienvenue dans l'application de gestion des patients");
        Print.log("Enregester");
        String email = Util.readString("Email",scanner);
        String password = Util.readString("Password",scanner);

        agentDto.email = email;
        agentDto.password = password;
        isAuthentificated = this.agentService.addAgent(agentDto);
        if (isAuthentificated){
            System.out.println("Success");
        }else {
            System.out.println("The account use reserved");
        }
    }

}
