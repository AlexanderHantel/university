package com.girevoy.university.service.impl;

import com.girevoy.university.dao.LessonDAO;
import com.girevoy.university.model.entity.Lesson;
import com.girevoy.university.model.entity.Student;
import com.girevoy.university.model.entity.Teacher;
import com.girevoy.university.model.timetable.StudentTimetable;
import com.girevoy.university.model.timetable.TeacherTimetable;
import com.girevoy.university.service.TimetableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class TimetableServiceImpl extends AbstractServiceImpl<Lesson> implements TimetableService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimetableServiceImpl.class);
    private final LessonDAO lessonDAO;

    @Autowired
    public TimetableServiceImpl(LessonDAO lessonDAO) {
        super(lessonDAO);
        this.lessonDAO = lessonDAO;
    }

    @Override
    public StudentTimetable createStudentDayTimetable(Student student, LocalDate day) {
        checkStudent(student);

        StudentTimetable studentTimetable = new StudentTimetable();

        studentTimetable.setStudent(student);
        studentTimetable.setGroup(student.getGroup());

        LocalDateTime startTime = day.atStartOfDay();
        LocalDateTime endTime = day.atTime(LocalTime.MAX);

        List<Lesson> schedule = lessonDAO.findAllFromDateToDateByGroupID(student.getGroup().getID(),
                                                                         startTime, endTime);
        studentTimetable.setSchedule(schedule);

        return studentTimetable;
    }

    @Override
    public StudentTimetable createStudentMonthTimetable(Student student, YearMonth yearMonth) {
        checkStudent(student);

        StudentTimetable studentTimetable = new StudentTimetable();

        studentTimetable.setStudent(student);
        studentTimetable.setGroup(student.getGroup());

        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        List<Lesson> schedule = lessonDAO.findAllFromDateToDateByGroupID(student.getGroup().getID(),
                                                                         startTime, endTime);
        studentTimetable.setSchedule(schedule);

        return studentTimetable;
    }

    private void checkStudent(Student student) {
        if (student == null || student.getGroup() == null || student.getGroup().getID() == 0) {
            throw new IllegalArgumentException("Student, group or group ID is null");
        }
    }

    @Override
    public TeacherTimetable createTeacherDayTimetable(Teacher teacher, LocalDate day) {
        checkTeacher(teacher);

        TeacherTimetable teacherTimetable = new TeacherTimetable();

        teacherTimetable.setTeacher(teacher);

        LocalDateTime startTime = day.atStartOfDay();
        LocalDateTime endTime = day.atTime(LocalTime.MAX);

        List<Lesson> schedule = lessonDAO.findAllFromDateToDateByTeacherID(teacher.getID(), startTime, endTime);
        teacherTimetable.setSchedule(schedule);

        return teacherTimetable;
    }

    @Override
    public TeacherTimetable createTeacherMonthTimetable(Teacher teacher, YearMonth yearMonth) {
        checkTeacher(teacher);

        TeacherTimetable teacherTimetable = new TeacherTimetable();

        teacherTimetable.setTeacher(teacher);

        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        List<Lesson> schedule = lessonDAO.findAllFromDateToDateByTeacherID(teacher.getID(), startTime, endTime);
        teacherTimetable.setSchedule(schedule);

        return teacherTimetable;
    }

    private void checkTeacher(Teacher teacher) {
        if (teacher == null || teacher.getID() == 0) {
            throw new IllegalArgumentException("Teacher or ID is null");
        }
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getObjectSimpleName() {
        return "Timetable";
    }

    @Override
    public Lesson findByID(long id) {
        return getDao().findByID(id).orElse(new Lesson());
    }
}
