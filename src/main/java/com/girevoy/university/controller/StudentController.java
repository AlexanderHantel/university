package com.girevoy.university.controller;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Group;
import com.girevoy.university.model.entity.Student;
import com.girevoy.university.service.FacultyService;
import com.girevoy.university.service.GroupService;
import com.girevoy.university.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    StudentService studentService;
    GroupService groupService;
    FacultyService facultyService;

    public StudentController(StudentService studentService, GroupService groupService, FacultyService facultyService) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.facultyService = facultyService;
    }

    @GetMapping()
    public String showAll(Model model) {
        List<Student> students = studentService.findAll();
        for (Student student : students) {
            Group group = groupService.findByID(student.getGroup().getID());
            Faculty faculty = facultyService.findByID(group.getFaculty().getID());
            group.setFaculty(faculty);
            student.setGroup(group);
        }
        model.addAttribute("students", students);
        return "/student/show-all";
    }

    @GetMapping("/{id}")
    public String showStudent(@PathVariable("id") int id, Model model) {
        Student student = studentService.findByID(id);
        Group group = groupService.findByID(student.getGroup().getID());
        Faculty faculty = facultyService.findByID(group.getFaculty().getID());

        group.setFaculty(faculty);
        student.setGroup(group);

        model.addAttribute("student", student);
        return "student/show-student";
    }

    @GetMapping("/{id}/{date}")
    public String showDaySchedule(@PathVariable("id") int id, @PathVariable("date") String date, Model model) {
        return "";
    }
}
