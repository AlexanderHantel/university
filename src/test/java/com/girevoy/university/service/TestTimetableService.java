package com.girevoy.university.service;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.model.entity.*;
import com.girevoy.university.model.timetable.StudentTimetable;
import com.girevoy.university.model.timetable.TeacherTimetable;
import com.girevoy.university.service.impl.TimetableServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestDataSourceConfig.class)
@Sql({"/createTables.sql", "/test-data.sql"})
@WebAppConfiguration
public class TestTimetableService {
    @Autowired
    private ApplicationContext context;
    TimetableService timetableService;

    @BeforeEach
    public void init() {
        timetableService = context.getBean("timetableService", TimetableServiceImpl.class);
    }

    @Test
    public void createStudentDayTimetableTest() {
        StudentTimetable expectedTimetable = new StudentTimetable(
                List.of(
                    new Lesson(1,
                               LocalDateTime.of(2000, 1, 1, 9, 0, 0),
                               new Theme(1),
                               new Room(1),
                               new Group(1),
                               new Teacher(1),
                               new University(1)),
                    new Lesson(2,
                               LocalDateTime.of(2000, 1, 1, 11, 0, 0),
                               new Theme(3),
                               new Room(2),
                               new Group(1),
                               new Teacher(2),
                               new University(1))),
                new Student(1, "Test", new Group(1)),
                new Group(1, "TestGroup"));

        StudentTimetable actualTimetable = timetableService.createStudentDayTimetable(
                new Student(1, "Test", new Group(1)), LocalDate.of(2000, 1, 1));

        assertEquals(expectedTimetable, actualTimetable);
    }

    @Test
    public void createStudentMonthTimetableTest() {
        StudentTimetable expectedTimetable = new StudentTimetable(
                List.of(
                        new Lesson(1,
                                   LocalDateTime.of(2000, 1, 1, 9, 0, 0),
                                   new Theme(1),
                                   new Room(1),
                                   new Group(1),
                                   new Teacher(1),
                                   new University(1)),
                        new Lesson(2,
                                   LocalDateTime.of(2000, 1, 1, 11, 0, 0),
                                   new Theme(3),
                                   new Room(2),
                                   new Group(1),
                                   new Teacher(2),
                                   new University(1))),
                new Student(1, "Test", new Group(1)),
                new Group(1, "TestGroup"));

        StudentTimetable actualTimetable = timetableService.createStudentMonthTimetable(
                new Student(1, "Test", new Group(1)), YearMonth.of(2000, 1));

        assertEquals(expectedTimetable, actualTimetable);
    }

    @Test
    public void createTeacherDayTimetableTest() {
        TeacherTimetable expectedTimetable = new TeacherTimetable(
                List.of(
                        new Lesson(1,
                                   LocalDateTime.of(2000, 1, 1, 9, 0, 0),
                                   new Theme(1),
                                   new Room(1),
                                   new Group(1),
                                   new Teacher(1),
                                   new University(1))),
                new Teacher(1, "Test"));

        TeacherTimetable actualTimetable = timetableService.createTeacherDayTimetable(
                new Teacher(1, "Test"), LocalDate.of(2000, 1, 1));

        assertEquals(expectedTimetable, actualTimetable);
    }

    @Test
    public void createTeacherMonthTimetableTest() {
        Map<String, String> map = new HashMap<>();
        TeacherTimetable expectedTimetable = new TeacherTimetable(
                List.of(
                        new Lesson(2,
                                   LocalDateTime.of(2000, 1, 1, 11, 0, 0),
                                   new Theme(3),
                                   new Room(2),
                                   new Group(1),
                                   new Teacher(2),
                                   new University(1))),
                new Teacher(2, "Test"));

        TeacherTimetable actualTimetable = timetableService.createTeacherMonthTimetable(
                new Teacher(2, "Test"), YearMonth.of(2000, 1));

        assertEquals(expectedTimetable, actualTimetable);
    }
}
