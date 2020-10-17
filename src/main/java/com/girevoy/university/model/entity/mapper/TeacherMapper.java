package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Teacher;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherMapper implements RowMapper<Teacher> {

    @Override
    public Teacher mapRow(ResultSet resultSet, int i) throws SQLException {
        Teacher teacher = new Teacher();

        teacher.setID(resultSet.getLong("id"));
        teacher.setName(resultSet.getString("name"));

        Faculty faculty = new Faculty();
        faculty.setID(resultSet.getLong("faculty_id"));
        teacher.setFaculty(faculty);

        Department department = new Department();
        department.setID(resultSet.getLong("department_id"));
        teacher.setDepartment(department);

        return teacher;
    }
}
