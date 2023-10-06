package ma.yc.service.impl;

import ma.yc.GUI.MainCompanyGUI;
import ma.yc.Mapper.impl.CompanyMapper;
import ma.yc.dao.impl.CompanyDaoImpl;
import ma.yc.dto.CompanyDto;
import ma.yc.model.Company;
import ma.yc.service.CompanyService;

import java.security.SecureRandom;

public class CompanyServiceImp implements CompanyService {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private Company company;
    private CompanyDaoImpl companyDao;
    private CompanyMapper companyMapper;
    public CompanyServiceImp(){
        this.companyDao = new CompanyDaoImpl();
        this.companyMapper = new CompanyMapper();
    }
    @Override
    public boolean addCompany(CompanyDto companyDto) {
        boolean result = false;
        this.company =  this.companyMapper.toEntity(companyDto);
        boolean resultSave = companyDao.saveCompany(company);
        if (resultSave){
            result = true;
        }
        return result;

    }

    @Override
    public boolean checkExisting(CompanyDto companyDto) {

        Company company1 = companyMapper.toEntity(companyDto);
        boolean result = this.companyDao.checkExisting(company1);
        return result;
    }
}
