package ma.yc.Mapper.impl;

import ma.yc.Mapper.Mapper;
import ma.yc.dto.ReportSalaryDto;
import ma.yc.model.RapotSalaire;

import java.sql.PreparedStatement;

public class RaportSalaryMapper implements Mapper<ReportSalaryDto,RapotSalaire> {


    @Override
    public RapotSalaire toEntity() {
        return null;
    }

    @Override

    public RapotSalaire toEntity(ReportSalaryDto reportSalaryDto) {
        RapotSalaire raportSalary = new RapotSalaire.RaportSalaireBuilder().
                setSalaire(reportSalaryDto.salaire)
                .setNbrJour(reportSalaryDto.jourTravail)
                .setPatient(reportSalaryDto.patient)
                .build();
        return raportSalary;
    }

    @Override
    public ReportSalaryDto toDto() {
        return null;
    }

    @Override
    public ReportSalaryDto toDto(RapotSalaire rapotSalaire) {
        return null;
    }

    @Override
    public PreparedStatement toPreparedStatement(RapotSalaire rapotSalaire, PreparedStatement preparedStatement) {
        return null;
    }
}
