package com.girevoy.university.dao;

import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

import static java.lang.String.format;
import static java.lang.String.valueOf;

@Component
public class DepartmentDAO extends AbstractDAO<Department>{
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDAO.class);

    @Autowired
    public DepartmentDAO(DataSource dataSource) {
        super(dataSource, "department");
    }

    @Override
    public boolean update(Department department) {
        int rows;
        String facultyID = (department.getFaculty() == null || department.getFaculty().getID() == 0) ?
                            "null" : valueOf(department.getFaculty().getID());

        rows = super.getJdbcTemplate().update(format("UPDATE %s SET name = ?, faculty_id = %s WHERE id = ?;",
                                                     super.getTableName(), facultyID),
                                              department.getName(), department.getID());
        LOGGER.debug("{} departments were updated", rows);

        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(Department department) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", department.getName());

        if (department.getFaculty() != null) {
            parameters.put("faculty_id", department.getFaculty().getID());
        }

        return parameters;
    }

    @Override
    public RowMapper<Department> getMapper() {
        return new DepartmentMapper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
