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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestDataSourceConfig.class)
@Sql({"/createTables.sql", "/test-data.sql"})
@WebAppConfiguration
public class TestFacultyDAO {
    @Autowired
    private ApplicationContext context;
    FacultyDAO facultyDAO;

    @BeforeEach
    public void init() {
        facultyDAO = context.getBean("facultyDAO", FacultyDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfFaculty() {
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setID(1);
        expectedFaculty.setName("Arts");

        assertEquals(Optional.of(expectedFaculty), facultyDAO.findByID(1));
    }

    @Test
    public void findByIDMethod_ShouldSetUniversityID() {
        Faculty expectedFaculty = facultyDAO.findByID(1).orElse(new Faculty());
        assertEquals(1, expectedFaculty.getUniversity().getID());
    }

    @Test
    public void insertProcess_ShouldInsertFacultyAndGenerateID() throws DAOException {
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setName("Test");

        expectedFaculty = facultyDAO.insert(expectedFaculty);

        Faculty actualFaculty = facultyDAO.findByID(3).orElse(new Faculty());
        assertEquals(expectedFaculty, actualFaculty);
    }

    @Test
    public void insertMethod_ShouldSetToFacultyGeneratedID() throws DAOException {
        Faculty actualFaculty = new Faculty();
        actualFaculty.setName("Test");

        actualFaculty = facultyDAO.insert(actualFaculty);

        assertEquals(3, actualFaculty.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfFacultyWithID() throws DAOException {
        Faculty actualFaculty = new Faculty();
        actualFaculty.setID(10);
        actualFaculty.setName("Test");

        facultyDAO.insert(actualFaculty);

        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setID(3);
        expectedFaculty.setName("Test");

        assertEquals(expectedFaculty, actualFaculty);
    }

    @Test
    public void insertProcess_ShouldInsertUniversityID() throws DAOException {
        Faculty expectedFaculty = new Faculty("Test", new University(2));

        facultyDAO.insert(expectedFaculty);

        Faculty actualFaculty = facultyDAO.findByID(3).orElse(new Faculty());

        assertEquals(expectedFaculty.getUniversity().getID(), actualFaculty.getUniversity().getID());
    }

    @Test
    public void updateMethod_ShouldUpdateAllFieldsOfFaculty_IfExist() {
        Faculty expectedFaculty = new Faculty(2, "Test", new University(2));

        facultyDAO.update(expectedFaculty);

        Faculty actualFaculty = facultyDAO.findByID(2).orElse(new Faculty());

        assertTrue(expectedFaculty.equals(actualFaculty) &&
                   expectedFaculty.getUniversity().getID() == actualFaculty.getUniversity().getID());
    }

    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        Faculty faculty = new Faculty();
        faculty.setID(2);
        faculty.setName("Test");

        assertTrue(facultyDAO.update(faculty));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchFaculty() {
        Faculty faculty = new Faculty();
        faculty.setID(10);
        faculty.setName("Test");

        assertFalse(facultyDAO.update(faculty));
    }

    @Test
    public void updateMethod_ShouldSetUniversityIDInNull_IfFacultyObjectHaveNoUniversity() {
        Faculty expectedFaculty = new Faculty(2, "Test");

        facultyDAO.update(expectedFaculty);

        Faculty actualFaculty = facultyDAO.findByID(2).orElse(new Faculty());

        assertEquals(0, actualFaculty.getUniversity().getID());
    }

    @Test
    public void findAll_ShouldReturnListOfFaculties() {
        List<Faculty> expectedList = List.of(
                new Faculty(1, "Arts"),
                new Faculty(2, "Medicine"));

        assertEquals(expectedList, facultyDAO.findAll());
    }

    @Test
    public void findAll_ShouldReturnFacultiesWithUniversityIDs() {
        List<Faculty> actualList = facultyDAO.findAll();

        assertTrue((1 == actualList.get(0).getUniversity().getID()) &&
                  (1 == actualList.get(1).getUniversity().getID()));
    }

    @Test
    public void delete_ShouldDeleteFaculty() {
        facultyDAO.delete(1);
        List<Faculty> expectedList = List.of(
                new Faculty(2, "Medicine"));

        assertEquals(expectedList, facultyDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(facultyDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(facultyDAO.delete(10));
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInGroupTable() {
        GroupDAO groupDAO = context.getBean("groupDAO", GroupDAO.class);

        facultyDAO.delete(1);

        assertTrue(groupDAO.findByID(1).orElse(new Group()).getFaculty().getID() == 0 &&
                   groupDAO.findByID(2).orElse(new Group()).getFaculty().getID() == 0);
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInDepartmentTable() {
        DepartmentDAO departmentDAO = context.getBean("departmentDAO", DepartmentDAO.class);

        facultyDAO.delete(1);

        assertTrue(departmentDAO.findByID(1).orElse(new Department()).getFaculty().getID() == 0 &&
                   departmentDAO.findByID(2).orElse(new Department()).getFaculty().getID() == 0);
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInSubjectTable() {
        SubjectDAO subjectDAO = context.getBean("subjectDAO", SubjectDAO.class);

        facultyDAO.delete(1);

        assertTrue(subjectDAO.findByID(1).orElse(new Subject()).getFaculty().getID() == 0 &&
                   subjectDAO.findByID(2).orElse(new Subject()).getFaculty().getID() == 0);
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInTeacherTable() {
        TeacherDAO teacherDAO = context.getBean("teacherDAO", TeacherDAO.class);

        facultyDAO.delete(1);

        assertTrue(teacherDAO.findByID(1).orElse(new Teacher()).getFaculty().getID() == 0 &&
                   teacherDAO.findByID(2).orElse(new Teacher()).getFaculty().getID() == 0);
    }
}
