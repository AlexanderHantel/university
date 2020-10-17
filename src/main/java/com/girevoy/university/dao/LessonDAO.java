package com.girevoy.university.dao;

import com.girevoy.university.exception.dao.WrongPeriodException;
import com.girevoy.university.model.entity.*;
import com.girevoy.university.model.entity.mapper.LessonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.String.format;
import static java.lang.String.valueOf;

@Component
public class LessonDAO extends AbstractDAO<Lesson> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonDAO.class);

    @Autowired
    public LessonDAO(DataSource dataSource) {
        super(dataSource, "lesson");
    }

    @Override
    public boolean update(Lesson lesson) {
        int rows;
        String themeID = (lesson.getTheme() == null || lesson.getTheme().getID() == 0) ?
                          "null" : valueOf(lesson.getTheme().getID());

        String roomID = (lesson.getRoom() == null || lesson.getRoom().getID() == 0) ?
                         "null" : valueOf(lesson.getRoom().getID());

        String groupID = (lesson.getGroup() == null || lesson.getGroup().getID() == 0) ?
                          "null" : valueOf(lesson.getGroup().getID());

        String teacherID = (lesson.getTeacher() == null || lesson.getTeacher().getID() == 0) ?
                            "null" : valueOf(lesson.getTeacher().getID());

        String universityID = (lesson.getUniversity() == null || lesson.getUniversity().getID() == 0) ?
                               "null" : valueOf(lesson.getUniversity().getID());

        rows =  super.getJdbcTemplate().update(format("UPDATE %s SET date_time = ?, theme_id = %s, " +
                                                      "room_id = %s, group_id = %s, teacher_id = %s, " +
                                                      "university_id = %s WHERE id = ?;", super.getTableName(),
                                                      themeID, roomID, groupID, teacherID, universityID),
                                               lesson.getDateTime(), lesson.getID());
        LOGGER.debug("{} lessons were updated", rows);

        return rows > 0;
    }

    @Override
    public Map<String, Object> getInsertedParameters(Lesson lesson) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date_time", lesson.getDateTime());

        if (lesson.getTheme() != null) {
            parameters.put("theme_id", lesson.getTheme().getID());
        }

        if (lesson.getRoom() != null) {
            parameters.put("room_id", lesson.getRoom().getID());
        }

        if (lesson.getGroup() != null) {
            parameters.put("group_id", lesson.getGroup().getID());
        }

        if (lesson.getTeacher() != null) {
            parameters.put("teacher_id", lesson.getTeacher().getID());
        }

        if (lesson.getUniversity() != null) {
            parameters.put("university_id", lesson.getUniversity().getID());
        }

        return parameters;
    }

    @Override
    public RowMapper<Lesson> getMapper() {
        return new LessonMapper();
    }

    public List<Lesson> findAllFromDateToDateByGroupID(long groupID, LocalDateTime startTime, LocalDateTime endTime) {
        String sqlQuery = format("SELECT * FROM lesson WHERE group_id = %d " +
                                 "AND date_time BETWEEN '%s' AND '%s' ORDER BY date_time;",
                                 groupID, startTime.toString(), endTime.toString());
        return findAllByQuery(sqlQuery);
    }

    public List<Lesson> findAllFromDateToDateByTeacherID(long teacherID, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new WrongPeriodException("Start-time is after end-time");
        }

        String sqlQuery = format("SELECT * FROM lesson WHERE teacher_id = %d " +
                                 "AND date_time BETWEEN '%s' AND '%s' ORDER BY date_time;",
                                 teacherID, startTime.toString(), endTime.toString());
        return findAllByQuery(sqlQuery);
    }

    private List<Lesson> findAllByQuery(String sqlQuery) {
        List<Lesson> lessons = new ArrayList<>();

        List<Map<String, Object>> rows;

        rows = getJdbcTemplate().queryForList(sqlQuery);
        LOGGER.debug("{} lessons were found", rows.size());

        for (Map<String, Object> row: rows) {
            Lesson lesson = parseMapRowToLesson(row);
            lessons.add(lesson);
        }

        return lessons;
    }

    private Lesson parseMapRowToLesson(Map<String, Object> row) {
        Lesson lesson = new Lesson();

        lesson.setID((long) row.get("ID"));

        String timestamp = row.get("DATE_TIME").toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");

        lesson.setDateTime(LocalDateTime.parse(timestamp, formatter));
        lesson.setRoom(new Room((long) row.get("ROOM_ID")));
        lesson.setTheme(new Theme((long) row.get("THEME_ID")));
        lesson.setUniversity(new University((long) row.get("UNIVERSITY_ID")));
        lesson.setGroup(new Group((long) row.get("GROUP_ID")));
        lesson.setTeacher(new Teacher((long) row.get("TEACHER_ID")));

        return lesson;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
