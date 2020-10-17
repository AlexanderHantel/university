package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Subject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectMapper implements RowMapper<Subject> {

    @Override
    public Subject mapRow(ResultSet resultSet, int i) throws SQLException {
        Subject subject = new Subject();

        subject.setID(resultSet.getLong("id"));
        subject.setName(resultSet.getString("name"));
        subject.setHours(resultSet.getInt("hours"));

        Faculty faculty = new Faculty();
        faculty.setID(resultSet.getLong("faculty_id"));
        subject.setFaculty(faculty);

        return subject;
    }
}
