package com.girevoy.university.dao;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Group;
import com.girevoy.university.model.entity.mapper.GroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.lang.String.valueOf;

@Component
public class GroupDAO extends AbstractDAO<Group>{
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDAO.class);
    private static final String FIND_ALL_WITH_STUDENTS_NUMBER_QUERY =
            "SELECT g.id, g.name, g.faculty_id, count(*) AS \"students\" " +
            "FROM groups g JOIN student " +
            "ON g.id=student.group_id GROUP BY g.id, g.name, g.faculty_id ORDER BY g.id;";

    @Autowired
    public GroupDAO(DataSource dataSource) {
        super(dataSource, "groups");
    }

    @Override
    public boolean update(Group group) {
        int rows;
        String facultyID = (group.getFaculty() == null || group.getFaculty().getID() == 0) ?
                            "null" : valueOf(group.getFaculty().getID());

        rows = super.getJdbcTemplate().update(format("UPDATE %s SET name = ?, faculty_id = %s WHERE id = ?;",
                                                     super.getTableName(), facultyID),
                                               group.getName(), group.getID());
        LOGGER.debug("{} groups were updated", rows);

        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(Group group) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", group.getName());

        if (group.getFaculty() != null) {
            parameters.put("faculty_id", group.getFaculty().getID());
        }

        return parameters;
    }

    @Override
    public RowMapper<Group> getMapper() {
        return new GroupMapper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    public Map<Group, Long> findAllWithStudentsNumber() {
        Map<Group, Long> resultMap = new HashMap<>();
        List<Map<String, Object>> rows = super.getJdbcTemplate().queryForList(FIND_ALL_WITH_STUDENTS_NUMBER_QUERY);
        for (Map<String, Object> row: rows) {
            Group group = new Group();
            group.setID((long) row.get("ID"));
            group.setName((String) row.get("NAME"));
            group.setFaculty(new Faculty((long) row.get("FACULTY_ID")));
            long students = (long) row.get("STUDENTS");

            resultMap.put(group, students);
        }

        LOGGER.debug("{} groups with students number were found", resultMap.size());

        return resultMap;
    }
}
