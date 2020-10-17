package com.girevoy.university.dao;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.exception.dao.DAOException;
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

@SpringJUnitConfig(TestDataSourceConfig.class)
@Sql({"/createTables.sql", "/test-data.sql"})
@WebAppConfiguration
public class TestRoomDAO {
    @Autowired
    private ApplicationContext context;
    RoomDAO roomDAO;

    @BeforeEach
    public void init() {
        roomDAO = context.getBean("roomDAO", RoomDAO.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void findByIDMethod_ShouldReturnOptionalOfRoom() {
        Room expectedRoom = new Room();
        expectedRoom.setID(1);
        expectedRoom.setNumber("A101");

        assertEquals(Optional.of(expectedRoom), roomDAO.findByID(1));
    }

    @Test
    public void insertProcess_ShouldInsertUniversityID() throws DAOException {
        Room expectedRoom = new Room("E201", new University(2));

        roomDAO.insert(expectedRoom);

        Room actualRoom = roomDAO.findByID(5).orElse(new Room());

        assertEquals(expectedRoom.getUniversity().getID(), actualRoom.getUniversity().getID());
    }

    @Test
    public void insertProcess_ShouldInsertRoomAndGenerateID() throws DAOException {
        Room expectedRoom = new Room();
        expectedRoom.setNumber("E201");

        expectedRoom = roomDAO.insert(expectedRoom);

        Room actualRoom = roomDAO.findByID(5).orElse(new Room());
        assertEquals(expectedRoom, actualRoom);
    }

    @Test
    public void insertMethod_ShouldSetToRoomGeneratedID() throws DAOException {
        Room actualRoom = new Room();
        actualRoom.setNumber("E201");

        actualRoom = roomDAO.insert(actualRoom);

        assertEquals(5, actualRoom.getID());
    }

    @Test
    public void insertProcess_ShouldChangeIDToGeneratedID_IfRoomWithID() throws DAOException {
        Room actualRoom = new Room();
        actualRoom.setID(4);
        actualRoom.setNumber("E201");

        roomDAO.insert(actualRoom);

        assertEquals(5, actualRoom.getID());
    }

    @Test
    public void updateMethod_ShouldUpdateAllFieldsOfRoom_IfExist() {
        Room expectedRoom = new Room(2, "E201", new University(2));

        roomDAO.update(expectedRoom);

        Room actualRoom = roomDAO.findByID(2).orElse(new Room());

        assertTrue(expectedRoom.equals(actualRoom) &&
                   expectedRoom.getUniversity().getID() == actualRoom.getUniversity().getID());
    }

    @Test
    public void updateMethod_ShouldReturnTrue_IfUpdateWasSuccessful() {
        Room room = new Room();
        room.setID(4);
        room.setNumber("E201");

        assertTrue(roomDAO.update(room));
    }

    @Test
    public void updateMethod_ShouldReturnFalse_IfNoSuchFaculty() {
        Room room = new Room();
        room.setID(10);
        room.setNumber("E201");

        assertFalse(roomDAO.update(room));
    }

    @Test
    public void updateMethod_ShouldSetUniversityIDInNull_IfRoomObjectHaveNoUniversity() {
        Room expectedRoom = new Room(2, "Test");

        roomDAO.update(expectedRoom);

        Room actualRoom = roomDAO.findByID(2).orElse(new Room());

        assertEquals(0, actualRoom.getUniversity().getID());
    }

    @Test
    public void findAll_ShouldReturnListOfRooms() {
        List<Room> expectedList = List.of(
                new Room(1, "A101"),
                new Room(2, "B102"),
                new Room(3, "C104"),
                new Room(4, "D105"));

        assertEquals(expectedList, roomDAO.findAll());
    }

    @Test
    public void findAll_ShouldReturnFacultiesWithUniversityIDs() {
        List<Room> actualList = roomDAO.findAll();

        assertTrue((1 == actualList.get(0).getUniversity().getID()) &&
                   (1 == actualList.get(1).getUniversity().getID()) &&
                   (2 == actualList.get(2).getUniversity().getID()) &&
                   (2 == actualList.get(3).getUniversity().getID()));
    }

    @Test
    public void delete_ShouldDeleteRoom() {
        roomDAO.delete(1);
        List<Room> expectedList = List.of(
                new Room(2, "B102"),
                new Room(3, "C104"),
                new Room(4, "D105"));

        assertEquals(expectedList, roomDAO.findAll());
    }

    @Test
    public void delete_ShouldReturnTrue_IfDeleteWasSuccessful() {
        assertTrue(roomDAO.delete(1));
    }

    @Test
    public void delete_ShouldReturnFalse_IfNoSuchID() {
        assertFalse(roomDAO.delete(10));
    }

    @Test
    public void deleteProcess_ShouldSetToNullDependentFieldInLessonTable() {
        LessonDAO lessonDAO = context.getBean("lessonDAO", LessonDAO.class);

        roomDAO.delete(1);

        assertEquals(0, lessonDAO.findByID(1).orElse(new Lesson()).getRoom().getID());
    }
}
