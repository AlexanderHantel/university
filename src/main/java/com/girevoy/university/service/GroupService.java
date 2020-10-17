package com.girevoy.university.service;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Group;

import java.util.Map;

public interface GroupService extends Service<Group> {
    void assignGroupToFaculty(Group group, Faculty faculty);
    void removeGroupFromFaculty(Group group);
    Map<Group, Long> findAllWithStudentsNumber();
}
