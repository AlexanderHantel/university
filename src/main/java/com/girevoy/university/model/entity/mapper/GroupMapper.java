package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Group group = new Group();

        group.setID(resultSet.getLong("id"));
        group.setName(resultSet.getString("name"));

        Faculty faculty = new Faculty();
        faculty.setID(resultSet.getLong("faculty_id"));
        group.setFaculty(faculty);

        return group;
    }
}
