package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.Group;
import com.girevoy.university.model.entity.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();

        student.setID(resultSet.getLong("id"));
        student.setName(resultSet.getString("name"));

        Group group = new Group();
        group.setID(resultSet.getLong("group_id"));
        student.setGroup(group);

        return student;
    }
}
