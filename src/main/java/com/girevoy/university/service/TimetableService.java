package com.girevoy.university.service;

import com.girevoy.university.model.entity.Lesson;
import com.girevoy.university.model.entity.Student;
import com.girevoy.university.model.entity.Teacher;
import com.girevoy.university.model.timetable.StudentTimetable;
import com.girevoy.university.model.timetable.TeacherTimetable;

import java.time.LocalDate;
import java.time.YearMonth;

public interface TimetableService extends Service<Lesson> {
    StudentTimetable createStudentDayTimetable(Student student, LocalDate day);
    StudentTimetable createStudentMonthTimetable(Student student, YearMonth yearMonth);
    TeacherTimetable createTeacherDayTimetable(Teacher teacher, LocalDate day);
    TeacherTimetable createTeacherMonthTimetable(Teacher teacher, YearMonth yearMonth);
}
