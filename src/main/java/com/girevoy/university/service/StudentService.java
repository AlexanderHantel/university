package com.girevoy.university.service;

import com.girevoy.university.model.entity.Group;
import com.girevoy.university.model.entity.Student;

public interface StudentService extends Service<Student> {
    void assignStudentToGroup(Student student, Group group);
    void removeStudentFromGroup(Student student);
}
