package com.girevoy.university.dao;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.exception.dao.DAOException;
import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Lesson;
import com.girevoy.university.model.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestDataSourceConfig.class)
@Sql({"/createTables.sql", "/test-data.sql"})
@WebAppConfiguration
public class TestTeacherDAO {
    @Autowired
    private ApplicationContext context;
    TeacherDAO teacherDAO;

    @BeforeEach
    public void init() {
        teacherDAO = context.getBean("teacherDAO", TeacherDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfTeacher() {
        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setID(1);
        expectedTeacher.setName("Edison");

        assertEquals(Optional.of(expectedTeacher), teacherDAO.findByID(1));
    }

    @Test
    public void findByIDMethod_ShouldSetFacultyID() {
        Teacher expectedTeacher = teacherDAO.findByID(1).orElse(new Teacher());
        assertEquals(1, expectedTeacher.getFaculty().getID());
    }

    @Test
    public void findByIDMethod_ShouldSetDepartmentID() {
        Teacher expectedTeacher = teacherDAO.findByID(1).orElse(new Teacher());
        assertEquals(1, expectedTeacher.getDepartment().getID());
    }

    @Test
    public void insertProcess_ShouldInsertTeacherAndGenerateID() throws DAOException {
        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setName("Test");

        expectedTeacher = teacherDAO.insert(expectedTeacher);

        Teacher actualTeacher = teacherDAO.findByID(5).orElse(new Teacher());
        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    public void insertMethod_ShouldSetToTeacherGeneratedID() throws DAOException {
        Teacher actualTeacher = new Teacher();
        actualTeacher.setName("Test");

        actualTeacher = teacherDAO.insert(actualTeacher);

        assertEquals(5, actualTeacher.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfTeacherWithID() throws DAOException {
        Teacher actualTeacher = new Teacher();
        actualTeacher.setID(10);
        actualTeacher.setName("Test");

        teacherDAO.insert(actualTeacher);

        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setID(5);
        expectedTeacher.setName("Test");

        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    public void insertProcess_ShouldInsertFacultyID() throws DAOException {
        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setName("Test");

        Faculty faculty = new Faculty();
        faculty.setID(2);

        expectedTeacher.setFaculty(faculty);

        teacherDAO.insert(expectedTeacher);

        Teacher actualTeacher = teacherDAO.findByID(5).orElse(new Teacher());

        assertEquals(expectedTeacher.getFaculty().getID(), actualTeacher.getFaculty().getID());
    }

    @Test
    public void insertProcess_ShouldInsertDepartmentID() throws DAOException {
        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setName("Test");

        Department department = new Department();
        department.setID(2);

        expectedTeacher.setDepartment(department);

        teacherDAO.insert(expectedTeacher);

        Teacher actualTeacher = teacherDAO.findByID(5).orElse(new Teacher());

        assertEquals(expectedTeacher.getDepartment().getID(), actualTeacher.getDepartment().getID());
    }

    @Test
    public void updateMethod_ShouldUpdateAllFieldsOfTeacher_IfExist() {
        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setID(4);
        expectedTeacher.setName("Test");

        Faculty faculty = new Faculty();
        faculty.setID(1);

        Department department = new Department();
        department.setID(1);

        expectedTeacher.setFaculty(faculty);
        expectedTeacher.setDepartment(department);

        teacherDAO.update(expectedTeacher);

        Teacher actualTeacher = teacherDAO.findByID(4).orElse(new Teacher());

        assertTrue(expectedTeacher.equals(actualTeacher) &&
                   expectedTeacher.getFaculty().getID() == actualTeacher.getFaculty().getID() &&
                   expectedTeacher.getDepartment().getID() == actualTeacher.getDepartment().getID());
    }

    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        Teacher teacher = new Teacher();
        teacher.setID(4);
        teacher.setName("Test");

        assertTrue(teacherDAO.update(teacher));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchTeacher() {
        Teacher teacher = new Teacher();
        teacher.setID(10);
        teacher.setName("Test");

        assertFalse(teacherDAO.update(teacher));
    }

    @Test
    public void updateMethod_ShouldSetFacultyIDInNull_IfTeacherObjectHaveNoFaculty() {
        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setID(4);
        expectedTeacher.setName("Test");

        teacherDAO.update(expectedTeacher);

        Teacher actualTeacher = teacherDAO.findByID(4).orElse(new Teacher());

        assertEquals(0, actualTeacher.getFaculty().getID());
    }

    @Test
    public void updateMethod_ShouldSetDepartmentIDInNull_IfTeacherObjectHaveNoDepartment() {
        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setID(4);
        expectedTeacher.setName("Test");

        teacherDAO.update(expectedTeacher);

        Teacher actualTeacher = teacherDAO.findByID(4).orElse(new Teacher());

        assertEquals(0, actualTeacher.getDepartment().getID());
    }

    @Test
    public void findAll_ShouldReturnListOfTeachers() {
        List<Teacher> expectedList = List.of(
                new Teacher(1, "Edison"),
                new Teacher(2, "Tesla"),
                new Teacher(3, "Einstein"),
                new Teacher(4, "Howking"));

        assertEquals(expectedList, teacherDAO.findAll());
    }

    @Test
    public void findAll_ShouldReturnTeachersWithFacultyIDs() {
        List<Teacher> actualList = teacherDAO.findAll();

        assertTrue((1 == actualList.get(0).getFaculty().getID()) &&
                   (1 == actualList.get(1).getFaculty().getID()) &&
                   (2 == actualList.get(2).getFaculty().getID()) &&
                   (2 == actualList.get(3).getFaculty().getID()));
    }

    @Test
    public void findAll_ShouldReturnTeachersWithDepartmentIDs() {
        List<Teacher> actualList = teacherDAO.findAll();

        assertTrue((1 == actualList.get(0).getDepartment().getID()) &&
                   (2 == actualList.get(1).getDepartment().getID()) &&
                   (3 == actualList.get(2).getDepartment().getID()) &&
                   (4 == actualList.get(3).getDepartment().getID()));
    }

    @Test
    public void delete_ShouldDeleteTeacher() {
        teacherDAO.delete(1);
        List<Teacher> expectedList = List.of(
                new Teacher(2, "Tesla"),
                new Teacher(3, "Einstein"),
                new Teacher(4, "Howking"));

        assertEquals(expectedList, teacherDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(teacherDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(teacherDAO.delete(10));
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInLessonTable() {
        LessonDAO lessonDAO = context.getBean("lessonDAO", LessonDAO.class);

        teacherDAO.delete(1);

        assertEquals(0, lessonDAO.findByID(1).orElse(new Lesson()).getTeacher().getID());
    }
}
