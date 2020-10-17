package com.girevoy.university.dao;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.exception.dao.DAOException;

import com.girevoy.university.model.entity.Group;
import com.girevoy.university.model.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(TestDataSourceConfig.class)
@Sql({"/createTables.sql", "/test-data.sql"})
@WebAppConfiguration
public class TestStudentDAO {
    @Autowired
    private ApplicationContext context;
    StudentDAO studentDAO;

    @BeforeEach
    public void init() {
        studentDAO = context.getBean("studentDAO", StudentDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfStudent() {
        Student expectedStudent = new Student();
        expectedStudent.setID(1);
        expectedStudent.setName("Johnson");

        assertEquals(Optional.of(expectedStudent), studentDAO.findByID(1));
    }

    @Test
    public void findByIDMethod_ShouldSetGroupID() {
        Student expectedStudent = studentDAO.findByID(1).orElse(new Student());
        assertEquals(1, expectedStudent.getGroup().getID());
    }

    @Test
    public void insertProcess_ShouldInsertStudentAndGenerateID() throws DAOException {
        Student expectedStudent = new Student();
        expectedStudent.setName("Test");

        expectedStudent = studentDAO.insert(expectedStudent);

        Student actualStudent = studentDAO.findByID(9).orElse(new Student());
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void insertMethod_ShouldSetToStudentGeneratedID() throws DAOException {
        Student actualStudent = new Student();
        actualStudent.setName("Test");

        actualStudent = studentDAO.insert(actualStudent);

        assertEquals(9, actualStudent.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfStudentWithID() throws DAOException {
        Student actualStudent = new Student();
        actualStudent.setID(15);
        actualStudent.setName("Test");

        studentDAO.insert(actualStudent);

        assertEquals(9, actualStudent.getID());
    }

    @Test
    public void insertProcess_ShouldInsertGroupID() throws DAOException {
        Student expectedStudent = new Student();
        expectedStudent.setName("Test");

        Group group = new Group();
        group.setID(2);

        expectedStudent.setGroup(group);

        studentDAO.insert(expectedStudent);

        Student actualStudent = studentDAO.findByID(9).orElse(new Student());

        assertEquals(expectedStudent.getGroup().getID(), actualStudent.getGroup().getID());
    }

    @Test
    public void updateMethod_ShouldUpdateAllFieldsOfStudent_IfExist() {
        Student expectedStudent = new Student();
        expectedStudent.setID(4);
        expectedStudent.setName("Test");
        Group group = new Group();
        group.setID(1);
        expectedStudent.setGroup(group);

        studentDAO.update(expectedStudent);

        Student actualStudent = studentDAO.findByID(4).orElse(new Student());

        assertTrue(expectedStudent.equals(actualStudent) &&
                expectedStudent.getGroup().getID() == actualStudent.getGroup().getID());
    }

    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        Student student = new Student();
        student.setID(4);
        student.setName("Test");

        assertTrue(studentDAO.update(student));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchStudent() {
        Student student = new Student();
        student.setID(10);
        student.setName("Test");

        assertFalse(studentDAO.update(student));
    }

    @Test
    public void updateMethod_ShouldSetGroupIDInNull_IfStudentObjectHaveNoGroup() {
        Student expectedStudent = new Student();
        expectedStudent.setID(4);
        expectedStudent.setName("Test");

        studentDAO.update(expectedStudent);

        Student actualStudent = studentDAO.findByID(4).orElse(new Student());

        assertEquals(0, actualStudent.getGroup().getID());
    }

    @Test
    public void findAll_ShouldReturnListOfStudents() {
        List<Student> expectedList = List.of(
                new Student(1, "Johnson"),
                new Student(2, "Jameson"),
                new Student(3, "Stevenson"),
                new Student(4, "Harrison"),
                new Student(5, "Black"),
                new Student(6, "Brown"),
                new Student(7, "White"),
                new Student(8, "Green"));

        assertEquals(expectedList, studentDAO.findAll());
    }

    @Test
    public void findAll_ShouldReturnStudentsWithGroupIDs() {
        List<Student> actualList = studentDAO.findAll();

        assertTrue((1 == actualList.get(0).getGroup().getID()) &&
                   (1 == actualList.get(1).getGroup().getID()) &&
                   (2 == actualList.get(2).getGroup().getID()) &&
                   (2 == actualList.get(3).getGroup().getID()) &&
                   (3 == actualList.get(4).getGroup().getID()) &&
                   (3 == actualList.get(5).getGroup().getID()) &&
                   (4 == actualList.get(6).getGroup().getID()) &&
                   (4 == actualList.get(7).getGroup().getID()));
    }

    @Test
    public void delete_ShouldDeleteStudent() {
        studentDAO.delete(1);
        List<Student> expectedList = List.of(
                new Student(2, "Jameson"),
                new Student(3, "Stevenson"),
                new Student(4, "Harrison"),
                new Student(5, "Black"),
                new Student(6, "Brown"),
                new Student(7, "White"),
                new Student(8, "Green"));

        assertEquals(expectedList, studentDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(studentDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(studentDAO.delete(10));
    }
}
