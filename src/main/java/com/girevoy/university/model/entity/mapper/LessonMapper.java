package com.girevoy.university.model.entity.mapper;

import com.girevoy.university.model.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonMapper implements RowMapper<Lesson> {
    @Override
    public Lesson mapRow(ResultSet resultSet, int i) throws SQLException {
        Lesson lesson = new Lesson();

        lesson.setID(resultSet.getLong("id"));
        lesson.setDateTime(resultSet.getTimestamp("date_time").toLocalDateTime());


        Theme theme = new Theme();
        theme.setID(resultSet.getLong("theme_id"));
        lesson.setTheme(theme);

        Room room = new Room();
        room.setID(resultSet.getLong("room_id"));
        lesson.setRoom(room);

        Group group = new Group();
        group.setID(resultSet.getLong("group_id"));
        lesson.setGroup(group);

        Teacher teacher = new Teacher();
        teacher.setID(resultSet.getLong("teacher_id"));
        lesson.setTeacher(teacher);

        University university = new University();
        university.setID(resultSet.getLong("university_id"));
        lesson.setUniversity(university);

        return lesson;
    }
}
