package com.girevoy.university.service;

import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Teacher;

public interface TeacherService extends Service<Teacher> {
    void assignTeacherToFaculty(Teacher teacher, Faculty faculty);
    void removeTeacherFromFaculty(Teacher teacher);
    void assignTeacherToDepartment(Teacher teacher, Department department);
    void removeTeacherFromDepartment(Teacher teacher);
}
