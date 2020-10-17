package com.girevoy.university.service;

import com.girevoy.university.model.entity.*;

public interface LessonService extends Service<Lesson> {
    void assignThemeToLesson(Lesson lesson, Theme theme);
    void removeThemeFromLesson(Lesson lesson);
    void assignRoomToLesson(Lesson lesson, Room room);
    void removeRoomFromLesson(Lesson lesson);
    void assignGroupToLesson(Lesson lesson, Group group);
    void removeGroupFromLesson(Lesson lesson);
    void assignTeacherToLesson(Lesson lesson, Teacher teacher);
    void removeTeacherFromLesson(Lesson lesson);
    void assignLessonToUniversity(Lesson lesson, University university);
    void removeLessonFromUniversity(Lesson lesson);
}
