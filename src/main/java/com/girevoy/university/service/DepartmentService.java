package com.girevoy.university.service;

import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;

public interface DepartmentService extends Service<Department> {
    void assignDepartmentToFaculty(Department department, Faculty faculty);
    void removeDepartmentFromFaculty(Department department);
}
