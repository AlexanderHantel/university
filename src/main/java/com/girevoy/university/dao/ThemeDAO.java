package com.girevoy.university.dao;

import com.girevoy.university.model.entity.Theme;
import com.girevoy.university.model.entity.mapper.ThemeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.lang.String.valueOf;

@Component
public class ThemeDAO extends AbstractDAO<Theme>{
    private static final Logger LOGGER = LoggerFactory.getLogger(ThemeDAO.class);

    @Autowired
    public ThemeDAO(DataSource dataSource) {
        super(dataSource, "theme");
    }

    @Override
    public boolean update(Theme theme) {
        int rows;
        String subjectID = (theme.getSubject() == null || theme.getSubject().getID() == 0) ?
                            "null" : valueOf(theme.getSubject().getID());

        rows = super.getJdbcTemplate().update(format("UPDATE %s SET description = ?, subject_id = %s " +
                                                     "WHERE id = ?;", super.getTableName(), subjectID),
                                              theme.getDescription(), theme.getID());
        LOGGER.debug("{} themes were updated", rows);

        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(Theme theme) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("description", theme.getDescription());

        if (theme.getSubject() != null) {
            parameters.put("subject_id", theme.getSubject().getID());
        }


        return parameters;
    }

    @Override
    public RowMapper<Theme> getMapper() {
        return new ThemeMapper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
