package com.girevoy.university.service.impl;

import com.girevoy.university.dao.LessonDAO;
import com.girevoy.university.service.LessonService;
import com.girevoy.university.model.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl extends AbstractServiceImpl<Lesson> implements LessonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonServiceImpl.class);

    @Autowired
    public LessonServiceImpl(LessonDAO lessonDAO) {
        super(lessonDAO);
    }

    @Override
    public void assignThemeToLesson(Lesson lesson, Theme theme) {
        lesson.setTheme(theme);
        update(lesson);
    }

    @Override
    public void removeThemeFromLesson(Lesson lesson) {
        lesson.setTheme(new Theme());
        update(lesson);
    }

    @Override
    public void assignRoomToLesson(Lesson lesson, Room room) {
        lesson.setRoom(room);
        update(lesson);
    }

    @Override
    public void removeRoomFromLesson(Lesson lesson) {
        lesson.setRoom(new Room());
        update(lesson);
    }

    @Override
    public void assignGroupToLesson(Lesson lesson, Group group) {
        lesson.setGroup(group);
        update(lesson);
    }

    @Override
    public void removeGroupFromLesson(Lesson lesson) {
        lesson.setGroup(new Group());
        update(lesson);
    }

    @Override
    public void assignTeacherToLesson(Lesson lesson, Teacher teacher) {
        lesson.setTeacher(teacher);
        update(lesson);
    }

    @Override
    public void removeTeacherFromLesson(Lesson lesson) {
        lesson.setTeacher(new Teacher());
        update(lesson);
    }

    @Override
    public void assignLessonToUniversity(Lesson lesson, University university) {
        lesson.setUniversity(university);
        update(lesson);
    }

    @Override
    public void removeLessonFromUniversity(Lesson lesson) {
        lesson.setUniversity(new University());
        update(lesson);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Lesson";
    }

    @Override
    public Lesson findByID(long id) {
        return getDao().findByID(id).orElse(new Lesson());
    }
}
