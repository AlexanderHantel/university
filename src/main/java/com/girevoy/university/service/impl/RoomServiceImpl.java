package com.girevoy.university.service.impl;

import com.girevoy.university.dao.RoomDAO;
import com.girevoy.university.model.entity.Room;
import com.girevoy.university.model.entity.University;
import com.girevoy.university.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl extends AbstractServiceImpl<Room> implements RoomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    public RoomServiceImpl(RoomDAO roomDAO) {
        super(roomDAO);
    }

    @Override
    public void assignRoomToUniversity(Room room, University university) {
        room.setUniversity(university);
        update(room);
    }

    @Override
    public void removeRoomFromUniversity(Room room) {
        room.setUniversity(new University());
        update(room);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Room";
    }

    @Override
    public Room findByID(long id) {
        return getDao().findByID(id).orElse(new Room());
    }
}
