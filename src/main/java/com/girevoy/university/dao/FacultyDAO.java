package com.girevoy.university.dao;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.mapper.FacultyMapper;
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
public class FacultyDAO extends AbstractDAO<Faculty>{
    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyDAO.class);

    @Autowired
    public FacultyDAO(DataSource dataSource) {
        super(dataSource, "faculty");
    }

    @Override
    public boolean update(Faculty faculty) {
        int rows;
        String universityID = (faculty.getUniversity() == null || faculty.getUniversity().getID() == 0) ?
                               "null" : valueOf(faculty.getUniversity().getID());

        rows = super.getJdbcTemplate().update(format("UPDATE %s SET name = ?, university_id = %s WHERE id = ?;",
                                                     super.getTableName(), universityID),
                                              faculty.getName(), faculty.getID());
        LOGGER.debug("{} faculties were updated", rows);


        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(Faculty faculty) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", faculty.getName());

        if (faculty.getUniversity() != null) {
            parameters.put("university_id", faculty.getUniversity().getID());
        }

        return parameters;
    }

    @Override
    public RowMapper<Faculty> getMapper() {
        return new FacultyMapper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
