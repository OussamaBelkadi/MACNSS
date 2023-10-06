package ma.yc.dao.impl;

import ma.yc.core.Print;
import ma.yc.core.SessionManager;
import ma.yc.dao.PatientDao;
import ma.yc.database.DatabaseConnection;
import ma.yc.model.Patient;
import ma.yc.model.RapotSalaire;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao{
    private  Connection connection;
    private Patient patient;
    private SessionManager sessionManager;


    public PatientDaoImpl() {
        this.sessionManager = new SessionManager();
       try{
           this.connection = DatabaseConnection.getInstance().getConnection();
       }catch (SQLException e){
              e.printStackTrace();
       }
    }

    @Override
    public boolean authentification(Patient p) {
        String query = "SELECT * FROM patients WHERE matricule = ?";
        try{
            var statement = this.connection.prepareStatement(query);
            statement.setString(1,p.getMatricule());
            var resultSet = statement.executeQuery();
            while (resultSet.next()){
                return true;
            }
        }catch (SQLException e){
            Print.log(e.toString());
        }

        return false;
    }

    @Override
    public boolean savePatient(Patient patient) {

        String query = "INSERT INTO `patients`(`matricule`, `societeID`, `Salaire`, `cotisationS`, `Numberwd`, `birthday`, `name`, `statusRetraitment`,`SalaireRetrait`) VALUES (?,?,?,?,?,?,?,'Ta pas droit au retraite',0)";
        boolean resultfin = false;
        try{
            String res = sessionManager.getSession("identifiant");
            Date date = Date.valueOf(patient.getBirthday());
            var statement = connection.prepareStatement(query);
            statement.setString(1, patient.getMatricule());
            statement.setString(2, patient.getCompany().getIdCompany());
            statement.setFloat(3, patient.getSalaire());
            statement.setFloat(4, patient.getCotisationS());
            statement.setInt(5, patient.getNumberwd());
            statement.setDate(6, date);
            statement.setString(7, patient.getNom());
            int result = statement.executeUpdate();
            if (result != 0){
                resultfin = true;
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return resultfin;
    }

    @Override
    public boolean saveDeclaration(Patient patient) {
        String query = "UPDATE `patients` SET  `Numberwd`= Numberwd+? WHERE  `matricule`= ?";
        boolean resultfin = false;
        try{

            var statement = connection.prepareStatement(query);
            statement.setInt(1, patient.getNumberwd());
            statement.setString(2, patient.getMatricule());

            int result = statement.executeUpdate();
            if (result != 0){
                resultfin = true;
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return resultfin;
    }

    @Override
    public float selectSalaire(Patient patient) {
        String query = "SELECT Salaire FROM `patients` WHERE  `matricule`= ?";
        float resultfin = 0;
        try{

            var statement = connection.prepareStatement(query);
            statement.setString(1, patient.getMatricule());

            ResultSet result = statement.executeQuery();
            if (result.next()){
                resultfin = result.getInt(1);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return resultfin;
    }

    @Override
    public int seelectDayWork(Patient patient) {
        String query = "SELECT Numberwd FROM `patients` WHERE  `matricule`= ?";
        int resultfin = 0;
        try{

            var statement = connection.prepareStatement(query);
            statement.setString(1, patient.getMatricule());

            ResultSet result = statement.executeQuery();
            if (result.next()){
                resultfin = result.getInt(1);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return resultfin;
    }

    @Override
    public java.util.Date selectbirthDay(Patient patient) {
        Date resultfin = null;
        String query = "SELECT birthday FROM `patients` WHERE  `matricule`= ?";
        try{

            var statement = connection.prepareStatement(query);
            statement.setString(1, patient.getMatricule());

            ResultSet result = statement.executeQuery();
            if (result.next()){
                resultfin = result.getDate(1);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return resultfin;
    }

    @Override
    public boolean saveSalaryRetirement(Patient patient) {
        String query = "UPDATE `patients` SET  `SalaireRetrait`= ?,statusRetraitment= 'Tu peux benificier du retraite' WHERE  `matricule`= ?";
        boolean resultfin = false;
        try{

            var statement = connection.prepareStatement(query);
            statement.setFloat(1, patient.getSalaireRetrait());
            statement.setString(2, patient.getMatricule());

            int result = statement.executeUpdate();
            if (result != 0){
                resultfin = true;
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return resultfin;
    }

    @Override
    public List<Patient> patientStatus(Patient patient) {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM `patients` WHERE matricule=?";
        try{
            var statement = this.connection.prepareStatement(query);
            statement.setString(1,patient.getMatricule());
            var resultSet = statement.executeQuery();
            while (resultSet.next()){
                Patient patient1 = new Patient();
                patient1.setMatricule(resultSet.getString("matricule"));
                patient1.setSalaire(resultSet.getFloat("Salaire"));
                patient1.setCotisationS(resultSet.getInt("cotisationS"));
                patient1.setNumberwd(resultSet.getInt("Numberwd"));
                patient1.setStatusRetraitment(resultSet.getString("statusRetraitment"));
                patient1.setSalaireRetrait(resultSet.getFloat("SalaireRetrait"));
                patient1.setBirthday(resultSet.getDate("birthday").toLocalDate());
                patient1.setNom(resultSet.getString("name"));

                patients.add(patient1);

            }
        }catch (SQLException e){
            Print.log(e.toString());
        }

        return patients;
    }

    @Override
    public List<RapotSalaire> selecteSalary(Patient patient) {
        List<RapotSalaire> rapotSalaireList = new ArrayList<>();
        String query = "SELECT salaire,jourTravail FROM reportsalaire WHERE patientId = ? ORDER By id DESC LIMIT 96;";
        float resultfin = 0;
        try{

            var statement = connection.prepareStatement(query);
            statement.setString(1, patient.getMatricule());

            ResultSet result = statement.executeQuery();
            RapotSalaire rapotSalaire = new RapotSalaire();
            while (result.next()){
                rapotSalaire.setSalaire(result.getFloat(1));
                rapotSalaire.setNbrJour(result.getInt(2));
                rapotSalaireList.add(rapotSalaire);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return rapotSalaireList;
    }
}
