package com.girevoy.university.service;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.University;

public interface FacultyService extends Service<Faculty> {
    void assignFacultyToUniversity(Faculty faculty, University university);
    void removeFacultyFromUniversity(Faculty faculty);
}
