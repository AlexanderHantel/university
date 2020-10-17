package com.girevoy.university.service;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Subject;

public interface SubjectService extends Service<Subject> {
    void assignSubjectToFaculty(Subject subject, Faculty faculty);
    void removeSubjectFromFaculty(Subject subject);
}
