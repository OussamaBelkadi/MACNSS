package ma.yc.service;

import ma.yc.dto.CompanyDto;
import ma.yc.model.Company;

public interface CompanyService {

    boolean addCompany(CompanyDto companyDto);
    boolean checkExisting(CompanyDto companyDto);
}
