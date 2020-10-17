package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.University;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyMapper implements RowMapper<Faculty> {
    @Override
    public Faculty mapRow(ResultSet resultSet, int i) throws SQLException {
        Faculty faculty = new Faculty();

        faculty.setID(resultSet.getLong("id"));
        faculty.setName(resultSet.getString("name"));

        University university = new University();
        university.setID(resultSet.getLong("university_id"));
        faculty.setUniversity(university);

        return faculty;
    }
}
