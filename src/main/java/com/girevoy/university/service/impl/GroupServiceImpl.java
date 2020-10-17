package com.girevoy.university.service.impl;

import com.girevoy.university.dao.GroupDAO;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Group;
import com.girevoy.university.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GroupServiceImpl extends AbstractServiceImpl<Group> implements GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
    private final GroupDAO groupDAO;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO) {
        super(groupDAO);
        this.groupDAO = groupDAO;
    }

    @Override
    public void assignGroupToFaculty(Group group, Faculty faculty) {
        group.setFaculty(faculty);
        update(group);
    }

    @Override
    public void removeGroupFromFaculty(Group group) {
        group.setFaculty(new Faculty());
        update(group);
    }

    @Override
    public Map<Group, Long> findAllWithStudentsNumber() {
        return groupDAO.findAllWithStudentsNumber();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Group";
    }

    @Override
    public Group findByID(long id) {
        return getDao().findByID(id).orElse(new Group());
    }
}
