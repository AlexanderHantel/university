package com.girevoy.university.model.timetable;

import com.girevoy.university.model.entity.Group;
import com.girevoy.university.model.entity.Lesson;
import com.girevoy.university.model.entity.Student;

import java.util.List;

public class StudentTimetable extends AbstractTimetable {
    private Student student;
    private Group group;

    public StudentTimetable() {
        super();
    }

    public StudentTimetable(List<Lesson> schedule, Student student, Group group) {
        super(schedule);
        this.student = student;
        this.group = group;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StudentTimetable that = (StudentTimetable) o;

        if (!getStudent().equals(that.getStudent())) return false;
        return getGroup().getID() == that.getGroup().getID();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + student.hashCode();
        result = 31 * result + group.hashCode();
        return result;
    }
}
