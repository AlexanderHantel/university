package com.girevoy.university.service.impl;

import com.girevoy.university.dao.DepartmentDAO;
import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends AbstractServiceImpl<Department> implements DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    public DepartmentServiceImpl(DepartmentDAO departmentDAO) {
        super(departmentDAO);
    }

    @Override
    public void assignDepartmentToFaculty(Department department, Faculty faculty) {
        department.setFaculty(faculty);
        update(department);
    }

    @Override
    public void removeDepartmentFromFaculty(Department department) {
        department.setFaculty(new Faculty());
        update(department);
    }

    @Override
    public Department findByID(long id) {
        return getDao().findByID(id).orElse(new Department());
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Department";
    }
}
