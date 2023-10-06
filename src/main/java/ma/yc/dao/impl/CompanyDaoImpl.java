package ma.yc.dao.impl;

import ma.yc.core.SessionManager;
import ma.yc.dao.CompanyDao;
import ma.yc.database.DatabaseConnection;
import ma.yc.model.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDaoImpl implements CompanyDao{

    private Connection connection;
    private SessionManager sessionManager;

    public CompanyDaoImpl(){
         this.sessionManager = new SessionManager();
        try{
            this.connection = DatabaseConnection.getInstance().getConnection();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }
    @Override
    public boolean saveCompany(Company company) {
        boolean result = false;
        String query = "INSERT INTO `societe`(`nom`, `idCompany`) VALUES (?,?)";
        try{
            var statement = this.connection.prepareStatement(query);
            statement.setString(1, company.getNomination());
            statement.setString(2, company.getIdCompany());
            int enregitrer = statement.executeUpdate();
            if (enregitrer != 0){
                result = true;
            }
        }catch(SQLException e){
            throw new RuntimeException();
        }
        return result;
    }

    @Override
    public boolean checkExisting(Company company) {
        boolean result = false;
        String query = "SELECT `nom`, `idCompany` From `societe` WHERE nom=? AND idCompany=?";
        try{
            var statement = this.connection.prepareStatement(query);
            statement.setString(1, company.getNomination());
            statement.setString(2, company.getIdCompany());
            ResultSet enregitrer = statement.executeQuery();
             while (enregitrer.next()){
                 sessionManager.setSession("identifiant", enregitrer.getString("idCompany"));
                result = true;
            }
        }catch(SQLException e){
            throw new RuntimeException();
        }
        return result;
    }
}
