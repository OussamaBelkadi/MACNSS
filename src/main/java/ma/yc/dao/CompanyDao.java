package ma.yc.dao;

import ma.yc.Mapper.impl.CompanyMapper;
import ma.yc.dao.impl.CompanyDaoImpl;
import ma.yc.model.Company;

public interface CompanyDao {

    boolean saveCompany(Company company);
    boolean checkExisting(Company company);
}
