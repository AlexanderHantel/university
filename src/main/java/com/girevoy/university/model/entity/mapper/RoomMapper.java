package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.Room;
import com.girevoy.university.model.entity.University;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper implements RowMapper<Room> {

    @Override
    public Room mapRow(ResultSet resultSet, int i) throws SQLException {
        Room room = new Room();

        room.setID(resultSet.getLong("id"));
        room.setNumber(resultSet.getString("number"));

        University university = new University();
        university.setID(resultSet.getLong("university_id"));
        room.setUniversity(university);

        return room;
    }
}
