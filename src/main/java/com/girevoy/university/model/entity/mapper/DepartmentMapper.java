package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
        Department department = new Department();

        department.setID(resultSet.getLong("id"));
        department.setName(resultSet.getString("name"));

        Faculty faculty = new Faculty();
        faculty.setID(resultSet.getLong("faculty_id"));
        department.setFaculty(faculty);

        return department;
    }
}
