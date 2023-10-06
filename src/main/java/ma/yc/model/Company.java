package ma.yc.model;

import java.util.Collection;

public class Company {
    private String idCompany;
    private String Nomination;

    public String getIdCompany() {
        return idCompany;
    }

    public String getNomination() {
        return Nomination;
    }

    public void setIdCompany(String idCompany){this.idCompany = idCompany;}
    @Override
    public String toString() {
        return "Comapany{"+
                "Nomination Company: " + Nomination
                + "}";
    }


    public static class CompanyBuilder{

        private Company company = new Company();
            public CompanyBuilder idCompany(String idCompany){
                company.idCompany = idCompany;
                return this;
            }

            public CompanyBuilder nomination(String nomination){
                company.Nomination = nomination;
                return this;
            }

            public Company build(){
                return company;
            }
    }
}
