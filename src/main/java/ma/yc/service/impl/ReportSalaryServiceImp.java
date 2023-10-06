package ma.yc.service.impl;

import ma.yc.Mapper.impl.RaportSalaryMapper;
import ma.yc.dao.ReportSalaryDao;
import ma.yc.dao.impl.ReportSalaryDaoImp;
import ma.yc.dto.ReportSalaryDto;
import ma.yc.model.Patient;
import ma.yc.model.RapotSalaire;
import ma.yc.service.ReportSalaryService;

public class ReportSalaryServiceImp implements ReportSalaryService {
    private ReportSalaryDao reportSalaryDao;
    private RaportSalaryMapper raportSalaryMapper;
    public ReportSalaryServiceImp(){
        this.reportSalaryDao = new ReportSalaryDaoImp();
        this.raportSalaryMapper = new RaportSalaryMapper();
    }
    @Override
    public boolean addSalary(ReportSalaryDto reportSalaryDto) {
        boolean result = false;



        RapotSalaire raportSalary = raportSalaryMapper.toEntity(reportSalaryDto);
        int accumulateDay = raportSalary.getNbrJour();
        float salary = raportSalary.getSalaire() / 26;
        if (accumulateDay > 26){
            int nbrMonth = accumulateDay/26;
            for (int i = 0; i < nbrMonth; i++){
                raportSalary.setNbrJour(26);
                raportSalary.setSalaire(salary);
                result = this.reportSalaryDao.addSalary(raportSalary);
            }
        } else if (accumulateDay <= 26) {
            result = this.reportSalaryDao.addSalary(raportSalary);
        }
        return result;
    }
}
