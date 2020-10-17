package com.girevoy.university.model.entity;

import java.time.LocalDateTime;

public class Lesson extends Entity {
    private LocalDateTime dateTime;
    private Theme theme;
    private Room room;
    private Group group;
    private Teacher teacher;
    private University university;

    public Lesson() {
    }

    public Lesson(long id) {
        super(id);
    }

    public Lesson(long id, LocalDateTime dateTime) {
        super(id);
        this.dateTime = dateTime;
    }

    public Lesson(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Lesson(long id, LocalDateTime dateTime, Theme theme, Room room, Group group, Teacher teacher, University university) {
        super(id);
        this.dateTime = dateTime;
        this.theme = theme;
        this.room = room;
        this.group = group;
        this.teacher = teacher;
        this.university = university;
    }

    public Lesson(LocalDateTime dateTime, Theme theme, Room room, Group group, Teacher teacher, University university) {
        this.dateTime = dateTime;
        this.theme = theme;
        this.room = room;
        this.group = group;
        this.teacher = teacher;
        this.university = university;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Lesson lesson = (Lesson) o;

        if (this.getID() != lesson.getID()) return false;

        return dateTime.equals(lesson.dateTime);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + dateTime.hashCode();
        return result;
    }
}
