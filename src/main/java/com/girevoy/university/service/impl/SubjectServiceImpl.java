package com.girevoy.university.service.impl;

import com.girevoy.university.dao.SubjectDAO;
import com.girevoy.university.model.entity.Subject;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl extends AbstractServiceImpl<Subject> implements SubjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Autowired
    public SubjectServiceImpl(SubjectDAO subjectDAO) {
        super(subjectDAO);
    }

    @Override
    public void assignSubjectToFaculty(Subject subject, Faculty faculty) {
        subject.setFaculty(faculty);
        update(subject);
    }

    @Override
    public void removeSubjectFromFaculty(Subject subject) {
        subject.setFaculty(new Faculty());
        update(subject);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Subject";
    }

    @Override
    public Subject findByID(long id) {
        return getDao().findByID(id).orElse(new Subject());
    }
}
