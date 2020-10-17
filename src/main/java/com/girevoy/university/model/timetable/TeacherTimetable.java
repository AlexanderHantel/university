package com.girevoy.university.model.timetable;

import com.girevoy.university.model.entity.Lesson;
import com.girevoy.university.model.entity.Teacher;

import java.util.List;

public class TeacherTimetable extends AbstractTimetable {
    private Teacher teacher;

    public TeacherTimetable() {
        super();
    }

    public TeacherTimetable(List<Lesson> schedule, Teacher teacher) {
        super(schedule);
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TeacherTimetable timetable = (TeacherTimetable) o;

        return getTeacher().equals(timetable.getTeacher());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getTeacher().hashCode();
        return result;
    }
}
