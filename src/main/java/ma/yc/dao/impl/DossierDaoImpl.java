package ma.yc.dao.impl;

import ma.yc.core.Print;
import ma.yc.dao.DossierDao;
import ma.yc.database.DatabaseConnection;

import ma.yc.enums.statusDossier;
import ma.yc.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DossierDaoImpl implements DossierDao {

    private Dossier dossier ;
    private List<Dossier> listdossier ;
    private Connection connection;
    public DossierDaoImpl(){
        try{
            connection = DatabaseConnection.getInstance().getConnection();
            dossier = new Dossier();
            listdossier =  new ArrayList<Dossier>();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

//    public DossierDaoImpl(Dossier dossier) {
//        this.dossier = dossier;
//    }

    @Override
    public boolean enregistrerDossier(Dossier dossier) {
        this.dossier = dossier;
        String QUERY = "INSERT INTO dossiers (numDossier, status, totalRemboursement, matricule) " +
                "VALUES(?,?,?,?)";

        //I
        try {
            // : save Dossier to Database
            PreparedStatement statement = connection.prepareStatement(QUERY);
            statement.setString(1,dossier.getNumDossier());
            statement.setString(2,dossier.getStatus().toString());
            //count total Reimbursement
            float totalRemboursement = this.totalRemoursement();
            statement.setFloat(3,totalRemboursement);
            statement.setString(4,dossier.getPatient().getMatricule());
            int isSaved = statement.executeUpdate();
            // : check if the dossier get saved ;
            if(isSaved ==1){
                // : if the type douc not null it will be inserted to database
                this.insertFicher(dossier);
                // : if the dossier has medicament it will be inserted to database
                if (dossier.getMedicaments().size()>0 || dossier.getMedicaments() != null){
                    dossier.getMedicaments().forEach(medicament -> {
                        this.saveMedicament(medicament,dossier);
                    });
                }
                if (dossier.getScanners().size()>0 || dossier.getScanners() != null){
                    dossier.getScanners().forEach(scanner -> {
                        this.saveScanner(scanner, dossier);
                    });
                }
                if (dossier.getAnalyses().size()>0 || dossier.getAnalyses() != null){
                    dossier.getAnalyses().forEach(analyse -> {
                        this.saveAnalyse(analyse,dossier);
                    });
                }
                if (dossier.getRadios().size()>0 || dossier.getRadios() != null){
                    dossier.getRadios().forEach(radio -> {
                        this.saveRadio(radio,dossier);
                    });
                }

                if (dossier.getVisites().size()>0 || dossier.getVisites() != null){
                    dossier.getVisites().forEach(visite -> {
                        this.saveVisit(visite,dossier);
                    });
                }

                Print.log("Dossier Dao =>"+ dossier.toString());
                return true;
            }


        }catch (SQLException e){
            Print.log(e.toString());
        }

        return false;
    }

    private int saveVisit(Visite visite, Dossier dossier) {
        try{
            String query = "INSERT INTO Visite (visiteId, prix, description, dossierNum)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1,visite.getVisiteId());
            statement.setFloat(2,visite.getPrix());
            statement.setString(3,visite.getDescription());
            statement.setString(4,dossier.getNumDossier());
            return statement.executeUpdate();
        }catch (SQLException e){
            Print.log(e.toString());
        }

        return  0;
    }

    private int saveRadio(Radio radio, Dossier dossier) {
        try{
            String query = "INSERT INTO radio (radioId, prix, description, dossierNum)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1,radio.getRadioId());
            statement.setFloat(2,radio.getPrix());
            statement.setString(3,radio.getDescription());
            statement.setString(4,dossier.getNumDossier());
            return statement.executeUpdate();
        }catch (SQLException e){
            Print.log(e.toString());
        }
        return 0;
    }

    private int saveAnalyse(Analyse analyse, Dossier dossier) {
        try{
            String query = "INSERT INTO analyse (analyseId, prix, description, dossierNum)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1,analyse.getAnalyseId());
            statement.setFloat(2,analyse.getPrix());
            statement.setString(3,analyse.getDescription());
            statement.setString(4,dossier.getNumDossier());
            return statement.executeUpdate();
        }catch (SQLException e){
            Print.log(e.toString());
        }
        return  0;
    }

    private int saveScanner(Scanner scanner, Dossier dossier) {
        try{
            String query = "INSERT INTO scanner (scannerId, prix, description, dossierNum)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1,scanner.getScannerId());
            statement.setFloat(2,scanner.getPrix());
            statement.setString(3,scanner.getDescription());
            statement.setString(4,dossier.getNumDossier());
            return statement.executeUpdate();
        }catch (SQLException e){
            Print.log(e.toString());
        }
        return 0;
    }

    private int saveMedicament(Medicament medicament, Dossier dossier) {
        try{
            String insertFicherQuery = "INSERT INTO medicament (codeBarre, quantite, prix, dossierNum)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement insertFicherStatement = connection.prepareStatement(insertFicherQuery);
            insertFicherStatement.setLong(1,medicament.getCodeBarre());
            insertFicherStatement.setInt(2,medicament.getQuantite());
            insertFicherStatement.setFloat(3,medicament.getPrix());
            insertFicherStatement.setString(4 ,dossier.getNumDossier());
            return insertFicherStatement.executeUpdate();

        }catch (SQLException e){
            Print.log(e.toString());
        }
        return  0 ;
    }


    public  int insertFicher(Dossier dossier){
        try{
            String insertFicherQuery = "INSERT INTO fichiers (numeroFichier, dateDepot, TotalFraisDossier, specialite, dossierNum)" +
                    "VALUES (?,?,?,?,?)";
            PreparedStatement insertFicherStatement = connection.prepareStatement(insertFicherQuery);
            insertFicherStatement.setLong(1,dossier.getFichier().getNumeroFicher());
            insertFicherStatement.setString(2,dossier.getFichier().getDateDepot().toString());
            insertFicherStatement.setFloat(3,dossier.getFichier().getTotalFraisDossier());
            insertFicherStatement.setString(4,dossier.getFichier().getSpecialite());
//            insertFicherStatement.setString(5,dossier.getPatient().getMatricule());
           return insertFicherStatement.executeUpdate();

        }catch (SQLException e){
            Print.log(e.toString());
        }
        return  0 ;
    }

    @Override
    public boolean modifierDossier() {
        //todo : change status
        return false;
    }

    @Override
    public float totalRemoursement() {
        //todo : calculate total remoursement
        return 0;
    }

    @Override
    public Dossier consulterDossier(String CodeDossier) {
        try {
            String Query = "SELECT *from dossiers where dossiers.numDossier = ?";

            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            preparedStatement.setString(1,CodeDossier);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                statusDossier status = null;
                String st =  resultSet.getString("status");
                if(st.equals(statusDossier.En_attend.toString())){
                    status = statusDossier.En_attend;
                }else if(st.equals(statusDossier.Refusé.toString())){
                    status = statusDossier.Refusé;
                }else if(st.equals(statusDossier.Validé.toString())){
                    status = statusDossier.Validé;
                }
                this.dossier.setTotalRemboursement(resultSet.getFloat("totalRemboursement")) ;
                this.dossier.setStatus(status);
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return this.dossier;
    }

    @Override
    public List<Dossier> consulterDossiers(String MatriculeUser) {
        try{
            List<Dossier> listdossier = new ArrayList<Dossier>();
            String Query = "SELECT *from dossiers where dossiers.matricule = ?";

            PreparedStatement preparedStatement = this.connection.prepareStatement(Query);
            preparedStatement.setString(1,MatriculeUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Dossier dossier1 = new Dossier();
                statusDossier status = null;
                String st = resultSet.getString("status");
                if(st.equals(statusDossier.En_attend.toString())){
                    status = statusDossier.En_attend;
                }else if(st.equals(statusDossier.Refusé.toString())){
                    status = statusDossier.Refusé;
                }else if(st.equals(statusDossier.Validé.toString())){
                    status = statusDossier.Validé;
                }
                dossier1.setTotalRemboursement(resultSet.getFloat("totalRemboursement"));
                dossier1.setNumDossier(resultSet.getString("numDossier"));
                dossier1.setStatus(status);

                this.listdossier.add(dossier1);

                //Dossier dossier1 = new Dossier(String numDossier, statusDossier status, resultSet.getFloat("totalRemboursement")));
                Print.log("total de remboursement ");


            }


        }catch(SQLException e ){
            e.printStackTrace();
        }


        return this.listdossier;
    }

    @Override
    public boolean suiviEtatDossier(String statusDossier) {
        //todo : change status of dossier in database
        return false;
    }

    @Override
    public boolean envoyeEmailChangemenetEtat(String statusDossier) {
        //todo : envoye email to user but i guess we don't need this method
        // because we have a method in the service
        return false;
    }

}
