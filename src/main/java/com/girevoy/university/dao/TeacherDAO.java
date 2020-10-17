package com.girevoy.university.dao;

import com.girevoy.university.model.entity.Teacher;
import com.girevoy.university.model.entity.mapper.TeacherMapper;
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
public class TeacherDAO extends AbstractDAO<Teacher>{
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherDAO.class);

    @Autowired
    public TeacherDAO(DataSource dataSource) {
        super(dataSource, "teacher");
    }

    @Override
    public boolean update(Teacher teacher) {
        int rows;
        String facultyID = (teacher.getFaculty() == null || teacher.getFaculty().getID() == 0) ?
                            "null" : valueOf(teacher.getFaculty().getID());

        String departmentID = (teacher.getDepartment() == null || teacher.getDepartment().getID() == 0) ?
                               "null" : valueOf(teacher.getDepartment().getID());

        rows =  super.getJdbcTemplate().update(format("UPDATE %s SET name = ?, faculty_id = %s, " +
                                                      "department_id = %s WHERE id = ?;", super.getTableName(),
                                                      facultyID, departmentID),
                                               teacher.getName(), teacher.getID());
        LOGGER.debug("{} teachers were updated", rows);

        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(Teacher teacher) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", teacher.getName());

        if (teacher.getFaculty() != null) {
            parameters.put("faculty_id", teacher.getFaculty().getID());
        }

        if (teacher.getDepartment() != null) {
            parameters.put("department_id", teacher.getDepartment().getID());
        }

        return parameters;
    }

    @Override
    public RowMapper<Teacher> getMapper() {
        return new TeacherMapper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
