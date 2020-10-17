package com.girevoy.university.dao;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.exception.dao.DAOException;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Subject;
import com.girevoy.university.model.entity.Theme;
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
public class TestSubjectDAO {
    @Autowired
    private ApplicationContext context;
    SubjectDAO subjectDAO;

    @BeforeEach
    public void init() {
        subjectDAO = context.getBean("subjectDAO", SubjectDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfSubject() {
        Subject expectedSubject = new Subject();
        expectedSubject.setID(1);
        expectedSubject.setName("Math");
        expectedSubject.setHours(12);

        assertEquals(Optional.of(expectedSubject), subjectDAO.findByID(1));
    }

    @Test
    public void findByIDMethod_ShouldSetFacultyID() {
        Subject expectedSubject = subjectDAO.findByID(1).orElse(new Subject());
        assertEquals(1, expectedSubject.getFaculty().getID());
    }

    @Test
    public void insertProcess_ShouldInsertSubjectAndGenerateID() throws DAOException {
        Subject expectedSubject = new Subject();
        expectedSubject.setName("Test");
        expectedSubject.setHours(20);

        expectedSubject = subjectDAO.insert(expectedSubject);

        Subject actualSubject = subjectDAO.findByID(5).orElse(new Subject());
        assertEquals(expectedSubject, actualSubject);
    }

    @Test
    public void insertMethod_ShouldSetToSubjectGeneratedID() throws DAOException {
        Subject actualSubject = new Subject();
        actualSubject.setName("Test");
        actualSubject.setHours(20);

        actualSubject = subjectDAO.insert(actualSubject);

        assertEquals(5, actualSubject.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfSubjectWithID() throws DAOException {
        Subject actualSubject = new Subject();
        actualSubject.setID(10);
        actualSubject.setName("Test");
        actualSubject.setHours(20);

        subjectDAO.insert(actualSubject);

        Subject expectedSubject = new Subject();
        expectedSubject.setID(5);
        expectedSubject.setName("Test");
        expectedSubject.setHours(20);

        assertEquals(expectedSubject, actualSubject);
    }

    @Test
    public void insertProcess_ShouldInsertFacultyID() throws DAOException {
        Subject expectedSubject = new Subject();
        expectedSubject.setName("Test");
        expectedSubject.setHours(20);

        Faculty faculty = new Faculty();
        faculty.setID(2);

        expectedSubject.setFaculty(faculty);

        subjectDAO.insert(expectedSubject);

        Subject actualSubject = subjectDAO.findByID(5).orElse(new Subject());

        assertEquals(expectedSubject.getFaculty().getID(), actualSubject.getFaculty().getID());
    }

    @Test
    public void updateMethod_ShouldUpdateAllFieldsOfSubject_IfExist() {
        Subject expectedSubject = new Subject();
        expectedSubject.setID(4);
        expectedSubject.setName("Test");
        expectedSubject.setHours(20);
        Faculty faculty = new Faculty();
        faculty.setID(1);
        expectedSubject.setFaculty(faculty);

        subjectDAO.update(expectedSubject);

        Subject actualSubject = subjectDAO.findByID(4).orElse(new Subject());

        assertTrue(expectedSubject.equals(actualSubject) &&
                   expectedSubject.getFaculty().getID() == actualSubject.getFaculty().getID());
    }

    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        Subject subject = new Subject();
        subject.setID(4);
        subject.setName("Test");
        subject.setHours(20);

        assertTrue(subjectDAO.update(subject));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchSubject() {
        Subject subject = new Subject();
        subject.setID(10);
        subject.setName("Test");
        subject.setHours(20);

        assertFalse(subjectDAO.update(subject));
    }

    @Test
    public void updateMethod_ShouldSetFacultyIDInNull_IfSubjectObjectHaveNoFaculty() {
        Subject expectedSubject = new Subject();
        expectedSubject.setID(4);
        expectedSubject.setName("Test");

        subjectDAO.update(expectedSubject);

        Subject actualSubject = subjectDAO.findByID(4).orElse(new Subject());

        assertEquals(0, actualSubject.getFaculty().getID());
    }

    @Test
    public void findAll_ShouldReturnListOfSubject() {
        List<Subject> expectedList = List.of(
                new Subject(1, "Math", 12),
                new Subject(2, "Biology", 12),
                new Subject(3, "Geometry", 12),
                new Subject(4, "Music", 12));

        assertEquals(expectedList, subjectDAO.findAll());
    }

    @Test
    public void findAll_ShouldReturnSubjectsWithFacultyIDs() {
        List<Subject> actualList = subjectDAO.findAll();

        assertTrue((1 == actualList.get(0).getFaculty().getID()) &&
                   (1 == actualList.get(1).getFaculty().getID()) &&
                   (2 == actualList.get(2).getFaculty().getID()) &&
                   (2 == actualList.get(3).getFaculty().getID()));
    }

    @Test
    public void delete_ShouldDeleteSubject() {
        subjectDAO.delete(1);
        List<Subject> expectedList = List.of(
                new Subject(2, "Biology", 12),
                new Subject(3, "Geometry", 12),
                new Subject(4, "Music", 12));

        assertEquals(expectedList, subjectDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(subjectDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(subjectDAO.delete(10));
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldsInThemeTable() {
        ThemeDAO themeDAO = context.getBean("themeDAO", ThemeDAO.class);

        subjectDAO.delete(1);

        assertTrue(themeDAO.findByID(1).orElse(new Theme()).getSubject().getID() == 0 &&
                   themeDAO.findByID(2).orElse(new Theme()).getSubject().getID() == 0);
    }
}
