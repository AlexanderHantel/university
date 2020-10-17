package com.girevoy.university.dao;

import com.girevoy.university.model.entity.Subject;
import com.girevoy.university.model.entity.mapper.SubjectMapper;
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
public class SubjectDAO extends AbstractDAO<Subject>{
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDAO.class);

    @Autowired
    public SubjectDAO(DataSource dataSource) {
        super(dataSource, "subject");
    }

    @Override
    public boolean update(Subject subject) {
        int rows;
        String facultyID = (subject.getFaculty() == null || subject.getFaculty().getID() == 0) ?
                            "null" : valueOf(subject.getFaculty().getID());

        rows =  super.getJdbcTemplate().update(format("UPDATE %s SET name = ?, hours = ?, faculty_id = %s " +
                                                      "WHERE id = ?;", super.getTableName(), facultyID),
                                               subject.getName(), subject.getHours(), subject.getID());
        LOGGER.debug("{} subjects were updated", rows);

        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(Subject subject) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", subject.getName());
        parameters.put("hours", subject.getHours());

        if (subject.getFaculty() != null) {
            parameters.put("faculty_id", subject.getFaculty().getID());
        }

        return parameters;
    }

    @Override
    public RowMapper<Subject> getMapper() {
        return new SubjectMapper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
