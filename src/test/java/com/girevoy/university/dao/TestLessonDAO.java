package com.girevoy.university.dao;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.exception.dao.DAOException;
import com.girevoy.university.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestDataSourceConfig.class)
@Sql({"/createTables.sql", "/test-data.sql"})
@WebAppConfiguration
public class TestLessonDAO {
    @Autowired
    private ApplicationContext context;
    LessonDAO lessonDAO;

    @BeforeEach
    public void init() {
        lessonDAO = context.getBean("lessonDAO", LessonDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfLesson() {
        Lesson expectedLesson = new Lesson();
        expectedLesson.setID(1);
        expectedLesson.setDateTime(LocalDateTime.of(2000, 1, 1, 9, 0, 0));

        assertEquals(Optional.of(expectedLesson), lessonDAO.findByID(1));
    }

    @Test
    public void findByIDMethod_ShouldSetThemeID() {
        Lesson expectedLesson = lessonDAO.findByID(1).orElse(new Lesson());
        assertEquals(1, expectedLesson.getTheme().getID());
    }

    @Test
    public void findByIDMethod_ShouldSetRoomID() {
        Lesson expectedLesson = lessonDAO.findByID(1).orElse(new Lesson());
        assertEquals(1, expectedLesson.getRoom().getID());
    }

    @Test
    public void findByIDMethod_ShouldSetGroupID() {
        Lesson expectedLesson = lessonDAO.findByID(1).orElse(new Lesson());
        assertEquals(1, expectedLesson.getGroup().getID());
    }

    @Test
    public void findByIDMethod_ShouldSetTeacherID() {
        Lesson expectedLesson = lessonDAO.findByID(1).orElse(new Lesson());
        assertEquals(1, expectedLesson.getTeacher().getID());
    }

    @Test
    public void findByIDMethod_ShouldSetUniversityID() {
        Lesson expectedLesson = lessonDAO.findByID(1).orElse(new Lesson());
        assertEquals(1, expectedLesson.getUniversity().getID());
    }

    @Test
    public void insertProcess_ShouldInsertLessonAndGenerateID() throws DAOException {
        Lesson expectedLesson = new Lesson(LocalDateTime.of(2000, 1, 13, 9, 15));

        expectedLesson = lessonDAO.insert(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(3).orElse(new Lesson());
        assertEquals(expectedLesson, actualLesson);
    }

    @Test
    public void insertMethod_ShouldSetToLessonGeneratedID() throws DAOException {
        Lesson actualLesson = new Lesson(LocalDateTime.of(2000, 1, 13, 9, 15));

        actualLesson = lessonDAO.insert(actualLesson);

        assertEquals(3, actualLesson.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfLessonWithID() throws DAOException {
        Lesson actualLesson = new Lesson(15, LocalDateTime.of(2000, 1, 13, 9, 15));

        lessonDAO.insert(actualLesson);

        assertEquals(3, actualLesson.getID());
    }

    @Test
    public void insertProcess_ShouldInsertThemeID() throws DAOException {
        Lesson expectedLesson = new Lesson(LocalDateTime.of(2000, 1, 13, 9, 15));

        Theme theme = new Theme();
        theme.setID(2);

        expectedLesson.setTheme(theme);

        lessonDAO.insert(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(3).orElse(new Lesson());

        assertEquals(expectedLesson.getTheme().getID(), actualLesson.getTheme().getID());
    }

    @Test
    public void insertProcess_ShouldInsertRoomID() throws DAOException {
        Lesson expectedLesson = new Lesson(LocalDateTime.of(2000, 1, 13, 9, 15));

        Room room = new Room();
        room.setID(2);

        expectedLesson.setRoom(room);

        lessonDAO.insert(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(3).orElse(new Lesson());

        assertEquals(expectedLesson.getRoom().getID(), actualLesson.getRoom().getID());
    }

    @Test
    public void insertProcess_ShouldInsertGroupID() throws DAOException {
        Lesson expectedLesson = new Lesson(LocalDateTime.of(2000, 1, 13, 9, 15));

        Group group = new Group();
        group.setID(2);

        expectedLesson.setGroup(group);

        lessonDAO.insert(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(3).orElse(new Lesson());

        assertEquals(expectedLesson.getGroup().getID(), actualLesson.getGroup().getID());
    }

    @Test
    public void insertProcess_ShouldInsertTeacherID() throws DAOException {
        Lesson expectedLesson = new Lesson(LocalDateTime.of(2000, 1, 13, 9, 15));

        Teacher teacher = new Teacher();
        teacher.setID(2);

        expectedLesson.setTeacher(teacher);

        lessonDAO.insert(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(3).orElse(new Lesson());

        assertEquals(expectedLesson.getTeacher().getID(), actualLesson.getTeacher().getID());
    }

    @Test
    public void insertProcess_ShouldInsertUniversityID() throws DAOException {
        Lesson expectedLesson = new Lesson(LocalDateTime.of(2000, 1, 13, 9, 15));
        expectedLesson.setUniversity(new University(2));

        lessonDAO.insert(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(3).orElse(new Lesson());

        assertEquals(expectedLesson.getUniversity().getID(), actualLesson.getUniversity().getID());
    }


    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        Lesson lesson = new Lesson();
        LocalDateTime dateTime = LocalDateTime.of(2001, 2, 2, 12, 1);
        lesson.setID(2);
        lesson.setDateTime(dateTime);

        assertTrue(lessonDAO.update(lesson));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchLesson() {
        Lesson lesson = new Lesson();
        LocalDateTime dateTime = LocalDateTime.of(2001, 2, 2, 12, 1);
        lesson.setID(10);
        lesson.setDateTime(dateTime);

        assertFalse(lessonDAO.update(lesson));
    }

    @Test
    public void updateMethod_ShouldUpdateAllFieldsOfDepartment_IfExist() {
        Lesson expectedLesson = new Lesson(2,
                                           LocalDateTime.of(2001, 2, 2, 12, 1),
                                           new Theme(1),
                                           new Room(1),
                                           new Group(2),
                                           new Teacher(1),
                                           new University(2));

        lessonDAO.update(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(2).orElse(new Lesson());

        assertTrue(expectedLesson.equals(actualLesson) &&
                   expectedLesson.getTheme().getID() == actualLesson.getTheme().getID() &&
                   expectedLesson.getRoom().getID() == actualLesson.getRoom().getID() &&
                   expectedLesson.getGroup().getID() == actualLesson.getGroup().getID() &&
                   expectedLesson.getTeacher().getID() == actualLesson.getTeacher().getID() &&
                   expectedLesson.getUniversity().getID() == actualLesson.getUniversity().getID());
    }

    @Test
    public void updateMethod_ShouldSetThemeIDInNull_IfLessonObjectHaveNoTheme() {
        Lesson expectedLesson = new Lesson();
        LocalDateTime dateTime = LocalDateTime.of(2001, 2, 2, 12, 1);
        expectedLesson.setID(2);
        expectedLesson.setDateTime(dateTime);

        lessonDAO.update(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(2).orElse(new Lesson());

        assertEquals(0, actualLesson.getTheme().getID());
    }

    @Test
    public void updateMethod_ShouldSetRoomIDInNull_IfLessonObjectHaveNoRoom() {
        Lesson expectedLesson = new Lesson();
        LocalDateTime dateTime = LocalDateTime.of(2001, 2, 2, 12, 1);
        expectedLesson.setID(2);
        expectedLesson.setDateTime(dateTime);

        lessonDAO.update(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(2).orElse(new Lesson());

        assertEquals(0, actualLesson.getRoom().getID());
    }

    @Test
    public void updateMethod_ShouldSetGroupIDInNull_IfLessonObjectHaveNoGroup() {
        Lesson expectedLesson = new Lesson();
        LocalDateTime dateTime = LocalDateTime.of(2001, 2, 2, 12, 1);
        expectedLesson.setID(2);
        expectedLesson.setDateTime(dateTime);

        lessonDAO.update(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(2).orElse(new Lesson());

        assertEquals(0, actualLesson.getGroup().getID());
    }

    @Test
    public void updateMethod_ShouldSetTeacherIDInNull_IfLessonObjectHaveNoTeacher() {
        Lesson expectedLesson = new Lesson();
        LocalDateTime dateTime = LocalDateTime.of(2001, 2, 2, 12, 1);
        expectedLesson.setID(2);
        expectedLesson.setDateTime(dateTime);

        lessonDAO.update(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(2).orElse(new Lesson());

        assertEquals(0, actualLesson.getTeacher().getID());
    }

    @Test
    public void updateMethod_ShouldSetUniversityIDInNull_IfLessonObjectHaveNoUniversity() {
        Lesson expectedLesson = new Lesson(2, LocalDateTime.of(2001, 2, 2, 12, 1));

        lessonDAO.update(expectedLesson);

        Lesson actualLesson = lessonDAO.findByID(2).orElse(new Lesson());

        assertEquals(0, actualLesson.getUniversity().getID());
    }


    @Test
    public void findAll_ShouldReturnListOfLessons() {
        List<Lesson> expectedList = List.of(
                new Lesson(1, LocalDateTime.of(2000, 1, 1, 9, 0, 0)),
                new Lesson(2, LocalDateTime.of(2000, 1, 1, 11, 0, 0)));

        assertEquals(expectedList, lessonDAO.findAll());
    }

    @Test
    public void findAll_ShouldReturnLessonsWithThemeIDs() {
        List<Lesson> actualList = lessonDAO.findAll();

        assertTrue((1 == actualList.get(0).getTheme().getID()) &&
                   (3 == actualList.get(1).getTheme().getID()));
    }

    @Test
    public void findAll_ShouldReturnLessonsWithRoomIDs() {
        List<Lesson> actualList = lessonDAO.findAll();

        assertTrue((1 == actualList.get(0).getRoom().getID()) &&
                   (2 == actualList.get(1).getRoom().getID()));
    }

    @Test
    public void findAll_ShouldReturnLessonsWithGroupIDs() {
        List<Lesson> actualList = lessonDAO.findAll();

        assertTrue((1 == actualList.get(0).getGroup().getID()) &&
                   (1 == actualList.get(1).getGroup().getID()));
    }

    @Test
    public void findAll_ShouldReturnLessonsWithTeacherIDs() {
        List<Lesson> actualList = lessonDAO.findAll();

        assertTrue((1 == actualList.get(0).getTeacher().getID()) &&
                   (2 == actualList.get(1).getTeacher().getID()));
    }

    @Test
    public void findAll_ShouldReturnLessonsWithUniversitiesIDs() {
        List<Lesson> actualList = lessonDAO.findAll();

        assertTrue((1 == actualList.get(0).getUniversity().getID()) &&
                   (1 == actualList.get(1).getUniversity().getID()));
    }

    @Test
    public void delete_ShouldDeleteLesson() {
        lessonDAO.delete(1);
        List<Lesson> expectedList = List.of(
                new Lesson(2, LocalDateTime.of(2000, 1, 1, 11, 0, 0)));

        assertEquals(expectedList, lessonDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(lessonDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(lessonDAO.delete(10));
    }

    @Test
    public void findAllByStudentAndDayTest() {
        List<Lesson> expected = List.of(
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
                           new University(1)));

        List<Lesson> actual = lessonDAO.findAllFromDateToDateByGroupID(1,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                LocalDateTime.of(2000, 1, 1, 23, 59, 59));

        assertEquals(expected, actual);
    }

    @Test
    public void findAllByStudentAndMonthTest() {
        List<Lesson> expected = List.of(
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
                           new University(1)));

        List<Lesson> actual = lessonDAO.findAllFromDateToDateByGroupID(1,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                LocalDateTime.of(2000, 1, 31, 23, 59, 59));

        assertEquals(expected, actual);
    }

    @Test
    public void findAllByTeacherAndDayTest() {
        List<Lesson> expected = List.of(
                new Lesson(1,
                           LocalDateTime.of(2000, 1, 1, 9, 0, 0),
                           new Theme(1),
                           new Room(1),
                           new Group(1),
                           new Teacher(1),
                           new University(1)));

        List<Lesson> actual = lessonDAO.findAllFromDateToDateByTeacherID(1,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                LocalDateTime.of(2000, 1, 1, 23, 59, 59));

        assertEquals(expected, actual);
    }

    @Test
    public void findAllByTeacherAndMonthTest() {
        List<Lesson> expected = List.of(
                new Lesson(2,
                           LocalDateTime.of(2000, 1, 1, 11, 0, 0),
                           new Theme(3),
                           new Room(2),
                           new Group(1),
                           new Teacher(2),
                           new University(1)));

        List<Lesson> actual = lessonDAO.findAllFromDateToDateByTeacherID(2,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                LocalDateTime.of(2000, 1, 31, 23, 59, 59));

        assertEquals(expected, actual);
    }
}
