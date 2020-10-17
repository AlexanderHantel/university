package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.University;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversityMapper implements RowMapper<University> {
    @Override
    public University mapRow(ResultSet resultSet, int i) throws SQLException {
        University university = new University();

        university.setID(resultSet.getLong("id"));
        university.setName(resultSet.getString("name"));

        return university;
    }
}
