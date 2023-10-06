package ma.yc.dao.impl;

import ma.yc.dao.ReportSalaryDao;
import ma.yc.database.DatabaseConnection;
import ma.yc.model.Patient;
import ma.yc.model.RapotSalaire;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class ReportSalaryDaoImp implements ReportSalaryDao {
    private Connection connection;

    public ReportSalaryDaoImp(){
        try{
            this.connection = DatabaseConnection.getInstance().getConnection();

        }catch(SQLException e){

        }
    }
    @Override
    public boolean addSalary(RapotSalaire rapotSalaire) {
        String query = "INSERT INTO `reportsalaire`(`salaire`, `patientId`, `jourTravail`) VALUES (?,?,?)";
        boolean resultfin = false;
        try{
            Patient patient = rapotSalaire.getPatient();

            String matricule = patient.getMatricule();
            var statement = connection.prepareStatement(query);
            statement.setFloat(1,rapotSalaire.getSalaire());
            statement.setString(2, matricule);
            statement.setInt(3, rapotSalaire.getNbrJour());

            int result = statement.executeUpdate();
            if (result != 0){
                resultfin = true;
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return resultfin;
    }
}
