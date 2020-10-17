package com.girevoy.university.dao;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.exception.dao.DAOException;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Lesson;
import com.girevoy.university.model.entity.Room;
import com.girevoy.university.model.entity.University;
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
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringJUnitConfig(TestDataSourceConfig.class)
@Sql({"/createTables.sql", "/test-data.sql"})
@WebAppConfiguration
public class TestUniversityDAO {
    @Autowired
    private ApplicationContext context;
    UniversityDAO universityDAO;

    @BeforeEach
    public void init() {
        universityDAO = context.getBean("universityDAO", UniversityDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfUniversity() {
        University expectedUniversity = new University();
        expectedUniversity.setID(1);
        expectedUniversity.setName("Stanford");

        assertEquals(Optional.of(expectedUniversity), universityDAO.findByID(1));
    }

    @Test
    public void insertProcess_ShouldInsertUniversityAndGenerateID() throws DAOException {
        University expectedUniversity = new University();
        expectedUniversity.setName("Test");

        expectedUniversity = universityDAO.insert(expectedUniversity);

        University actualUniversity = universityDAO.findByID(3).orElse(new University());
        assertEquals(expectedUniversity, actualUniversity);
    }

    @Test
    public void insertMethod_ShouldSetToUniversityGeneratedID() throws DAOException {
        University actualUniversity = new University();
        actualUniversity.setName("Test");

        actualUniversity = universityDAO.insert(actualUniversity);

        assertEquals(3, actualUniversity.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfUniversityWithID() throws DAOException {
        University actualUniversity = new University();
        actualUniversity.setID(10);
        actualUniversity.setName("Test");

        universityDAO.insert(actualUniversity);

        University expectedUniversity = new University();
        expectedUniversity.setID(3);
        expectedUniversity.setName("Test");

        assertEquals(expectedUniversity, actualUniversity);
    }

    @Test
    public void updateMethod_ShouldUpdateUniversity_IfExist() {
        University expectedUniversity = new University();
        expectedUniversity.setID(2);
        expectedUniversity.setName("Test");

        universityDAO.update(expectedUniversity);

        University actualUniversity = universityDAO.findByID(2).orElse(new University());

        assertEquals(expectedUniversity, actualUniversity);
    }

    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        University university = new University();
        university.setID(2);
        university.setName("Test");

        assertTrue(universityDAO.update(university));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchUniversity() {
        University university = new University();
        university.setID(10);
        university.setName("Test");

        assertFalse(universityDAO.update(university));
    }

    @Test
    public void findAll_ShouldReturnListOfFaculties() {
        List<University> expectedList = List.of(
                new University(1, "Stanford"),
                new University(2, "CollTech"));

        assertEquals(expectedList, universityDAO.findAll());
    }

    @Test
    public void delete_ShouldDeleteUniversity() {
        universityDAO.delete(1);
        List<University> expectedList = List.of(
                new University(2, "CollTech"));

        assertEquals(expectedList, universityDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(universityDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(universityDAO.delete(10));
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInFacultyTable() {
        FacultyDAO facultyDAO = context.getBean("facultyDAO", FacultyDAO.class);

        universityDAO.delete(1);

        assertTrue(0 == facultyDAO.findByID(1).orElse(new Faculty()).getUniversity().getID() &&
                   0 == facultyDAO.findByID(2).orElse(new Faculty()).getUniversity().getID());
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInRoomTable() {
        RoomDAO roomDAO = context.getBean("roomDAO", RoomDAO.class);

        universityDAO.delete(1);

        assertTrue(0 == roomDAO.findByID(1).orElse(new Room()).getUniversity().getID() &&
                   0 == roomDAO.findByID(2).orElse(new Room()).getUniversity().getID());
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInLessonTable() {
        LessonDAO lessonDAO = context.getBean("lessonDAO", LessonDAO.class);

        universityDAO.delete(1);

        assertTrue(0 == lessonDAO.findByID(1).orElse(new Lesson()).getUniversity().getID() &&
                   0 == lessonDAO.findByID(2).orElse(new Lesson()).getUniversity().getID());
    }
}
