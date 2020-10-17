package com.girevoy.university.service.impl;

import com.girevoy.university.dao.FacultyDAO;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.University;
import com.girevoy.university.service.FacultyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FacultyServiceImpl extends AbstractServiceImpl<Faculty> implements FacultyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyServiceImpl.class);

    @Autowired
    public FacultyServiceImpl(FacultyDAO facultyDAO) {
        super(facultyDAO);
    }

    @Override
    public void assignFacultyToUniversity(Faculty faculty, University university) {
        faculty.setUniversity(university);
        update(faculty);
    }

    @Override
    public void removeFacultyFromUniversity(Faculty faculty) {
        faculty.setUniversity(new University());
        update(faculty);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Faculty";
    }

    @Override
    public Faculty findByID(long id) {
        return getDao().findByID(id).orElse(new Faculty());
    }
}
