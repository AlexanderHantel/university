package com.girevoy.university.service.impl;

import com.girevoy.university.dao.StudentDAO;
import com.girevoy.university.model.entity.Student;
import com.girevoy.university.model.entity.Group;
import com.girevoy.university.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends AbstractServiceImpl<Student> implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        super(studentDAO);
    }

    @Override
    public void assignStudentToGroup(Student student, Group group) {
        student.setGroup(group);
        update(student);
    }

    @Override
    public void removeStudentFromGroup(Student student) {
        student.setGroup(new Group());
        update(student);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Student";
    }

    @Override
    public Student findByID(long id) {
        return getDao().findByID(id).orElse(new Student());
    }
}
