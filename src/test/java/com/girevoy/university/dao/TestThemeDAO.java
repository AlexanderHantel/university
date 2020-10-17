package com.girevoy.university.dao;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.exception.dao.DAOException;
import com.girevoy.university.model.entity.Lesson;
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
public class TestThemeDAO {
    @Autowired
    private ApplicationContext context;
    ThemeDAO themeDAO;

    @BeforeEach
    public void init() {
        themeDAO = context.getBean("themeDAO", ThemeDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfTheme() {
        Theme expectedTheme = new Theme();
        expectedTheme.setID(1);
        expectedTheme.setDescription("Aaaaaa");

        assertEquals(Optional.of(expectedTheme), themeDAO.findByID(1));
    }

    @Test
    public void findByIDMethod_ShouldSetSubjectID() {
        Theme expectedTheme = themeDAO.findByID(1).orElse(new Theme());
        assertEquals(1, expectedTheme.getSubject().getID());
    }

    @Test
    public void insertProcess_ShouldInsertThemeAndGenerateID() throws DAOException {
        Theme expectedTheme = new Theme();
        expectedTheme.setDescription("Test");

        expectedTheme = themeDAO.insert(expectedTheme);

        Theme actualTheme = themeDAO.findByID(5).orElse(new Theme());
        assertEquals(expectedTheme, actualTheme);
    }

    @Test
    public void insertMethod_ShouldSetToThemeGeneratedID() throws DAOException {
        Theme actualTheme = new Theme();
        actualTheme.setDescription("Test");

        actualTheme = themeDAO.insert(actualTheme);

        assertEquals(5, actualTheme.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfThemeWithID() throws DAOException {
        Theme actualTheme = new Theme();
        actualTheme.setID(10);
        actualTheme.setDescription("Test");

        themeDAO.insert(actualTheme);

        Theme expectedTheme = new Theme();
        expectedTheme.setID(5);
        expectedTheme.setDescription("Test");

        assertEquals(expectedTheme, actualTheme);
    }

    @Test
    public void insertProcess_ShouldInsertSubjectID() throws DAOException {
        Theme expectedTheme = new Theme();
        expectedTheme.setDescription("Test");

        Subject subject = new Subject();
        subject.setID(2);

        expectedTheme.setSubject(subject);

        themeDAO.insert(expectedTheme);

        Theme actualTheme = themeDAO.findByID(5).orElse(new Theme());

        assertEquals(expectedTheme.getSubject().getID(), actualTheme.getSubject().getID());
    }

    @Test
    public void updateMethod_ShouldUpdateAllFieldsOfTheme_IfExist() {
        Theme expectedTheme = new Theme();
        expectedTheme.setID(4);
        expectedTheme.setDescription("Test");
        Subject faculty = new Subject();
        faculty.setID(1);
        expectedTheme.setSubject(faculty);

        themeDAO.update(expectedTheme);

        Theme actualTheme = themeDAO.findByID(4).orElse(new Theme());

        assertTrue(expectedTheme.equals(actualTheme) &&
                   expectedTheme.getSubject().getID() == actualTheme.getSubject().getID());
    }

    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        Theme theme = new Theme();
        theme.setID(4);
        theme.setDescription("Test");

        assertTrue(themeDAO.update(theme));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchTheme() {
        Theme theme = new Theme();
        theme.setID(10);
        theme.setDescription("Test");

        assertFalse(themeDAO.update(theme));
    }

    @Test
    public void updateMethod_ShouldSetSubjectIDInNull_IfThemeObjectHaveNoSubject() {
        Theme expectedTheme = new Theme();
        expectedTheme.setID(4);
        expectedTheme.setDescription("Test");

        themeDAO.update(expectedTheme);

        Theme actualTheme = themeDAO.findByID(4).orElse(new Theme());

        assertEquals(0, actualTheme.getSubject().getID());
    }

    @Test
    public void findAll_ShouldReturnListOfThemes() {
        List<Theme> expectedList = List.of(
                new Theme(1, "Aaaaaa"),
                new Theme(2, "Bbbbbb"),
                new Theme(3, "Cccccc"),
                new Theme(4, "Dddddd"));

        assertEquals(expectedList, themeDAO.findAll());
    }

    @Test
    public void findAll_ShouldReturnThemesWithSubjectIDs() {
        List<Theme> actualList = themeDAO.findAll();

        assertTrue((1 == actualList.get(0).getSubject().getID()) &&
                   (1 == actualList.get(1).getSubject().getID()) &&
                   (2 == actualList.get(2).getSubject().getID()) &&
                   (2 == actualList.get(3).getSubject().getID()));
    }

    @Test
    public void delete_ShouldDeleteTheme() {
        themeDAO.delete(1);
        List<Theme> expectedList = List.of(
                new Theme(2, "Bbbbbb"),
                new Theme(3, "Cccccc"),
                new Theme(4, "Dddddd"));

        assertEquals(expectedList, themeDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(themeDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(themeDAO.delete(10));
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInLessonTable() {
        LessonDAO lessonDAO = context.getBean("lessonDAO", LessonDAO.class);

        themeDAO.delete(1);

        assertEquals(0, lessonDAO.findByID(1).orElse(new Lesson()).getTheme().getID());
    }
}
