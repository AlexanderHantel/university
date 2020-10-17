package com.girevoy.university.dao;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.exception.dao.DAOException;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Group;
import com.girevoy.university.model.entity.Lesson;
import com.girevoy.university.model.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestDataSourceConfig.class)
@WebAppConfiguration
@Sql({"/createTables.sql", "/test-data.sql"})
public class TestGroupDAO {
    @Autowired
    private ApplicationContext context;
    GroupDAO groupDAO;

    @BeforeEach
    public void init() {
        groupDAO = context.getBean("groupDAO", GroupDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfGroup() {
        Group expectedGroup = new Group();
        expectedGroup.setID(1);
        expectedGroup.setName("AR-01");

        assertEquals(Optional.of(expectedGroup), groupDAO.findByID(1));
    }

    @Test
    public void findByIDMethod_ShouldSetFacultyID() {
        Group expectedGroup = groupDAO.findByID(1).orElse(new Group());
        assertEquals(1, expectedGroup.getFaculty().getID());
    }

    @Test
    public void insertProcess_ShouldInsertGroupAndGenerateID() throws DAOException {
        Group expectedGroup = new Group();
        expectedGroup.setName("Test");

        expectedGroup = groupDAO.insert(expectedGroup);

        Group actualGroup = groupDAO.findByID(5).orElse(new Group());
        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    public void insertMethod_ShouldSetToGroupGeneratedID() throws DAOException {
        Group actualGroup = new Group();
        actualGroup.setName("Test");

        actualGroup = groupDAO.insert(actualGroup);

        assertEquals(5, actualGroup.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfGroupWithID() throws DAOException {
        Group actualGroup = new Group();
        actualGroup.setID(10);
        actualGroup.setName("Test");

        groupDAO.insert(actualGroup);

        assertEquals(5, actualGroup.getID());
    }

    @Test
    public void insertProcess_ShouldInsertFacultyID() throws DAOException {
        Group expectedGroup = new Group();
        expectedGroup.setName("Test");

        Faculty faculty = new Faculty();
        faculty.setID(2);

        expectedGroup.setFaculty(faculty);

        groupDAO.insert(expectedGroup);

        Group actualGroup = groupDAO.findByID(5).orElse(new Group());

        assertEquals(expectedGroup.getFaculty().getID(), actualGroup.getFaculty().getID());
    }

    @Test
    public void updateMethod_ShouldUpdateAllFieldsOfGroup_IfExist() {
        Group expectedGroup = new Group();
        expectedGroup.setID(4);
        expectedGroup.setName("Test");
        Faculty faculty = new Faculty();
        faculty.setID(1);
        expectedGroup.setFaculty(faculty);

        groupDAO.update(expectedGroup);

        Group actualGroup = groupDAO.findByID(4).orElse(new Group());

        assertTrue(expectedGroup.equals(actualGroup) &&
                expectedGroup.getFaculty().getID() == actualGroup.getFaculty().getID());
    }

    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        Group group = new Group();
        group.setID(4);
        group.setName("Test");

        assertTrue(groupDAO.update(group));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchGroup() {
        Group group = new Group();
        group.setID(10);
        group.setName("Test");

        assertFalse(groupDAO.update(group));
    }

    @Test
    public void updateMethod_ShouldSetFacultyIDInNull_IfGroupObjectHaveNoFaculty() {
        Group expectedGroup = new Group();
        expectedGroup.setID(4);
        expectedGroup.setName("Test");

        groupDAO.update(expectedGroup);

        Group actualGroup = groupDAO.findByID(4).orElse(new Group());

        assertEquals(0, actualGroup.getFaculty().getID());
    }

    @Test
    public void findAll_ShouldReturnListOfGroups() {
        List<Group> expectedList = List.of(
                new Group(1, "AR-01"),
                new Group(2, "AR-02"),
                new Group(3, "MD-01"),
                new Group(4, "MD-02"));

        assertEquals(expectedList, groupDAO.findAll());
    }

    @Test
    public void findAll_ShouldReturnGroupsWithFacultyIDs() {
        List<Group> actualList = groupDAO.findAll();

        assertTrue((1 == actualList.get(0).getFaculty().getID()) &&
                (1 == actualList.get(1).getFaculty().getID()) &&
                (2 == actualList.get(2).getFaculty().getID()) &&
                (2 == actualList.get(3).getFaculty().getID()));
    }

    @Test
    public void delete_ShouldDeleteGroup() {
        groupDAO.delete(1);
        List<Group> expectedList = List.of(
                new Group(2, "AR-02"),
                new Group(3, "MD-01"),
                new Group(4, "MD-02"));

        assertEquals(expectedList, groupDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(groupDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(groupDAO.delete(10));
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInStudentTable() {
        StudentDAO studentDAO = context.getBean("studentDAO", StudentDAO.class);

        groupDAO.delete(1);

        assertTrue(studentDAO.findByID(1).orElse(new Student()).getGroup().getID() == 0 &&
                   studentDAO.findByID(2).orElse(new Student()).getGroup().getID() == 0);
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInLessonTable() {
        LessonDAO lessonDAO = context.getBean("lessonDAO", LessonDAO.class);

        groupDAO.delete(1);

        assertTrue(lessonDAO.findByID(1).orElse(new Lesson()).getGroup().getID() == 0 &&
                   lessonDAO.findByID(2).orElse(new Lesson()).getGroup().getID() == 0);
    }

    @Test
    public void findAllWithStudentsNumber_ShouldReturnMap() {
        Map<Group, Long> expectedMap = Map.of(
                new Group(1, "AR-01", new Faculty(1)), (long) 2,
                new Group(2, "AR-02", new Faculty(1)), (long) 2,
                new Group(3, "MD-01", new Faculty(2)), (long) 2,
                new Group(4, "MD-02", new Faculty(2)), (long) 2
        );
        Map<Group, Long> actualMap = groupDAO.findAllWithStudentsNumber();

        assertEquals(expectedMap ,actualMap);
    }
}
