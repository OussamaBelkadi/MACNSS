package ma.yc.Mapper.impl;

import ma.yc.Mapper.Mapper;
import ma.yc.dto.AdminDto;
import ma.yc.dto.CompanyDto;
import ma.yc.dto.UserDto;
import ma.yc.model.Administrateur;
import ma.yc.model.Company;
import ma.yc.model.Utilisateur;

import java.sql.PreparedStatement;

public class AdminMapper implements Mapper<AdminDto, Administrateur> {


    @Override
    public Administrateur toEntity() {
        return null;
    }

    @Override
    public Administrateur toEntity(AdminDto adminDto) {
        Administrateur administrateur = new Administrateur();
        administrateur.setEmail(adminDto.email);
        administrateur.setPassword(adminDto.password);
        return  administrateur;
    }


    @Override
    public AdminDto toDto() {
        return null;
    }

    @Override
    public AdminDto toDto(Administrateur t) {
        return null;
    }

    @Override
    public PreparedStatement toPreparedStatement(Administrateur administrateur, PreparedStatement preparedStatement) {
        return null;
    }
}
