package com.girevoy.university.dao;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.exception.dao.DAOException;
import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(TestDataSourceConfig.class)
@Sql({"/createTables.sql", "/test-data.sql"})
@WebAppConfiguration
public class TestDepartmentDAO {
    @Autowired
    private ApplicationContext context;
    DepartmentDAO departmentDAO;

    @BeforeEach
    public void init() {
        departmentDAO = context.getBean("departmentDAO", DepartmentDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfDepartment() {
        Department expectedDepartment = new Department();
        expectedDepartment.setID(1);
        expectedDepartment.setName("Classics");

        assertEquals(Optional.of(expectedDepartment), departmentDAO.findByID(1));
    }

    @Test
    public void findByIDMethod_ShouldSetFacultyID() {
        Department expectedDepartment = departmentDAO.findByID(1).orElse(new Department());
        assertEquals(1, expectedDepartment.getFaculty().getID());
    }

    @Test
    public void insertProcess_ShouldInsertDepartmentAndGenerateID() throws DAOException {
        Department expectedDepartment = new Department();
        expectedDepartment.setName("Test");

        expectedDepartment = departmentDAO.insert(expectedDepartment);

        Department actualDepartment = departmentDAO.findByID(5).orElse(new Department());
        assertEquals(expectedDepartment, actualDepartment);
    }

    @Test
    public void insertMethod_ShouldSetToDepartmentGeneratedID() throws DAOException {
        Department actualDepartment = new Department();
        actualDepartment.setName("Test");

        actualDepartment = departmentDAO.insert(actualDepartment);

        assertEquals(5, actualDepartment.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfDepartmentWithID() throws DAOException {
        Department actualDepartment = new Department();
        actualDepartment.setID(10);
        actualDepartment.setName("Test");

        departmentDAO.insert(actualDepartment);

        Department expectedDepartment = new Department();
        expectedDepartment.setID(5);
        expectedDepartment.setName("Test");

        assertEquals(expectedDepartment, actualDepartment);
    }

    @Test
    public void insertProcess_ShouldInsertFacultyID() throws DAOException {
        Department expectedDepartment = new Department();
        expectedDepartment.setName("Test");

        Faculty faculty = new Faculty();
        faculty.setID(2);

        expectedDepartment.setFaculty(faculty);

        departmentDAO.insert(expectedDepartment);

        Department actualDepartment = departmentDAO.findByID(5).orElse(new Department());

        assertEquals(expectedDepartment.getFaculty().getID(), actualDepartment.getFaculty().getID());
    }

    @Test
    public void updateMethod_ShouldUpdateAllFieldsOfDepartment_IfExist() {
        Department expectedDepartment = new Department();
        expectedDepartment.setID(4);
        expectedDepartment.setName("Test");
        Faculty faculty = new Faculty();
        faculty.setID(1);
        expectedDepartment.setFaculty(faculty);

        departmentDAO.update(expectedDepartment);

        Department actualDepartment = departmentDAO.findByID(4).orElse(new Department());

        assertTrue(expectedDepartment.equals(actualDepartment) &&
                   expectedDepartment.getFaculty().getID() == actualDepartment.getFaculty().getID());
    }

    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        Department department = new Department();
        department.setID(4);
        department.setName("Test");

        assertTrue(departmentDAO.update(department));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchDepartment() {
        Department department = new Department();
        department.setID(10);
        department.setName("Test");

        assertFalse(departmentDAO.update(department));
    }

    @Test
    public void updateMethod_ShouldSetFacultyIDInNull_IfDepartmentObjectHaveNoFaculty() {
        Department expectedDepartment = new Department(4 , "Test");

        departmentDAO.update(expectedDepartment);

        Department actualDepartment = departmentDAO.findByID(4).orElse(new Department());

        assertEquals(0, actualDepartment.getFaculty().getID());
    }

    @Test
    public void findAll_ShouldReturnListOfDepartments() {
        List<Department> expectedList = List.of(
                new Department(1, "Classics"),
                new Department(2, "Culture"),
                new Department(3, "Nursing"),
                new Department(4, "Midwifery"));

        assertEquals(expectedList, departmentDAO.findAll());
    }

    @Test
    public void findAll_ShouldReturnDepartmentsWithFacultyIDs() {
        List<Department> actualList = departmentDAO.findAll();

        assertTrue((1 == actualList.get(0).getFaculty().getID()) &&
                   (1 == actualList.get(1).getFaculty().getID()) &&
                   (2 == actualList.get(2).getFaculty().getID()) &&
                   (2 == actualList.get(3).getFaculty().getID()));
    }

    @Test
    public void delete_ShouldDeleteDepartment() {
        departmentDAO.delete(1);
        List<Department> expectedList = List.of(
                new Department(2, "Culture"),
                new Department(3, "Nursing"),
                new Department(4, "Midwifery"));

        assertEquals(expectedList, departmentDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(departmentDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(departmentDAO.delete(10));
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInTeacherTable() {
        TeacherDAO teacherDAO = context.getBean("teacherDAO", TeacherDAO.class);

        departmentDAO.delete(1);

        assertEquals(0, teacherDAO.findByID(1).orElse(new Teacher()).getDepartment().getID());
    }
}
