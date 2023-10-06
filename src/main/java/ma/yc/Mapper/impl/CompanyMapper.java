package ma.yc.Mapper.impl;

import ma.yc.Mapper.Mapper;
import ma.yc.dto.CompanyDto;
import ma.yc.model.Company;

import java.security.SecureRandom;
import java.sql.PreparedStatement;

public class CompanyMapper implements Mapper<CompanyDto,Company>
{

    @Override
    public Company toEntity() {
        return null;
    }

    @Override
    public Company toEntity(CompanyDto companyDto) {
        Company company = new Company.CompanyBuilder().
                nomination(companyDto.nomination)
                .idCompany(companyDto.identifiant)
                .build();
        return company;
    }

    @Override
    public CompanyDto toDto() {
        return null;
    }

    @Override
    public CompanyDto toDto(Company company) {
        return null;
    }

    @Override
    public PreparedStatement toPreparedStatement(Company company, PreparedStatement preparedStatement) {
        return null;
    }
}
