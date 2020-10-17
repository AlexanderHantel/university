package com.girevoy.university.service.impl;

import com.girevoy.university.dao.TeacherDAO;
import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Teacher;
import com.girevoy.university.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends AbstractServiceImpl<Teacher> implements TeacherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO) {
        super(teacherDAO);
    }

    @Override
    public void assignTeacherToFaculty(Teacher teacher, Faculty faculty) {
        teacher.setFaculty(faculty);
        update(teacher);
    }

    @Override
    public void removeTeacherFromFaculty(Teacher teacher) {
        teacher.setFaculty(new Faculty());
        update(teacher);
    }

    @Override
    public void assignTeacherToDepartment(Teacher teacher, Department department) {
        teacher.setDepartment(department);
        update(teacher);
    }

    @Override
    public void removeTeacherFromDepartment(Teacher teacher) {
        teacher.setDepartment(new Department());
        update(teacher);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Teacher";
    }

    @Override
    public Teacher findByID(long id) {
        return getDao().findByID(id).orElse(new Teacher());
    }
}
