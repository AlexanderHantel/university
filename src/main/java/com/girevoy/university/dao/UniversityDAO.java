package com.girevoy.university.dao;

import com.girevoy.university.model.entity.University;
import com.girevoy.university.model.entity.mapper.UniversityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@Component
public class UniversityDAO extends AbstractDAO<University> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityDAO.class);

    @Autowired
    public UniversityDAO(DataSource dataSource) {
        super(dataSource, "university");
    }

    @Override
    public boolean update(University university) {
        int rows;

        rows = super.getJdbcTemplate().update(format("UPDATE %s SET name = ? WHERE id = ?;",
                                                     super.getTableName()),
                                              university.getName(), university.getID());
        LOGGER.debug("{} universities were updated", rows);

        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(University university) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", university.getName());

        return parameters;
    }

    @Override
    public RowMapper<University> getMapper() {
        return new UniversityMapper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
