package com.girevoy.university.dao;

import com.girevoy.university.model.entity.Student;
import com.girevoy.university.model.entity.mapper.StudentMapper;
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
public class StudentDAO extends AbstractDAO<Student> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAO.class);

    @Autowired
    public StudentDAO(DataSource dataSource) {
        super(dataSource, "student");
    }

    @Override
    public boolean update(Student student) {
        int rows;
        String groupID = (student.getGroup() == null || student.getGroup().getID() == 0) ?
                          "null" : valueOf(student.getGroup().getID());

        rows =  super.getJdbcTemplate().update(format("UPDATE %s SET name = ?, group_id = %s WHERE id = ?;",
                                                      super.getTableName(), groupID),
                                               student.getName(), student.getID());
        LOGGER.debug("{} students were updated", rows);

        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(Student student) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", student.getName());

        if (student.getGroup() != null) {
            parameters.put("group_id", student.getGroup().getID());
        }

        return parameters;
    }

    @Override
    public RowMapper<Student> getMapper() {
        return new StudentMapper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
