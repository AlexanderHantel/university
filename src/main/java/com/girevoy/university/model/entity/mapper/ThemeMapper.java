package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.Subject;
import com.girevoy.university.model.entity.Theme;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMapper implements RowMapper<Theme> {

    @Override
    public Theme mapRow(ResultSet resultSet, int i) throws SQLException {
        Theme theme = new Theme();

        theme.setID(resultSet.getLong("id"));
        theme.setDescription(resultSet.getString("description"));

        Subject subject = new Subject();
        subject.setID(resultSet.getLong("subject_id"));
        theme.setSubject(subject);

        return theme;
    }
}
