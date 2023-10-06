package ma.yc.model;

public class Director {

    public static Company.CompanyBuilder companyBuilder(){
        return new Company.CompanyBuilder();
    }
    public static RapotSalaire.RaportSalaireBuilder raportBuikder(){ return new RapotSalaire.RaportSalaireBuilder(); }

}
