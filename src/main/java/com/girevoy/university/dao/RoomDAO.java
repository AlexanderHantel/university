package com.girevoy.university.dao;

import com.girevoy.university.model.entity.Room;
import com.girevoy.university.model.entity.mapper.RoomMapper;
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
public class RoomDAO extends AbstractDAO<Room> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomDAO.class);

    @Autowired
    public RoomDAO(DataSource dataSource) {
        super(dataSource, "room");
    }

    @Override
    public boolean update(Room room) {
        int rows;
        String universityID = (room.getUniversity() == null || room.getUniversity().getID() == 0) ?
                               "null" : valueOf(room.getUniversity().getID());

        rows =  super.getJdbcTemplate().update(format("UPDATE %s SET number = ?, university_id = %s " +
                                                      "WHERE id = ?;", super.getTableName(), universityID),
                                               room.getNumber(), room.getID());
        LOGGER.debug("{} rooms were updated", rows);

        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(Room room) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("number", room.getNumber());

        if (room.getUniversity() != null) {
            parameters.put("university_id", room.getUniversity().getID());
        }

        return parameters;
    }

    @Override
    public RowMapper<Room> getMapper() {
        return new RoomMapper();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
