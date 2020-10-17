package com.girevoy.university.service;

import com.girevoy.university.model.entity.Room;
import com.girevoy.university.model.entity.University;

public interface RoomService extends Service<Room> {
    void assignRoomToUniversity(Room room, University university);
    void removeRoomFromUniversity(Room room);
}
