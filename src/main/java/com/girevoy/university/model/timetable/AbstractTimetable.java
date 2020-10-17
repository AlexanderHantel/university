package com.girevoy.university.model.timetable;

import com.girevoy.university.model.entity.Lesson;

import java.util.List;

public abstract class AbstractTimetable {
    private List<Lesson> schedule;

    public AbstractTimetable() {
    }

    public AbstractTimetable(List<Lesson> schedule) {
        this.schedule = schedule;
    }

    public List<Lesson> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Lesson> schedule) {
        this.schedule = schedule;
    }

    public void addLesson(Lesson lesson) {
        schedule.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        schedule.remove(lesson);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractTimetable that = (AbstractTimetable) o;

        return schedule.equals(that.schedule);
    }

    @Override
    public int hashCode() {
        return schedule.hashCode();
    }
}
