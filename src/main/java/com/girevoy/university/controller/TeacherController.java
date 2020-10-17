package com.girevoy.university.controller;

import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Teacher;
import com.girevoy.university.service.DepartmentService;
import com.girevoy.university.service.FacultyService;
import com.girevoy.university.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    TeacherService teacherService;
    FacultyService facultyService;
    DepartmentService departmentService;

    public TeacherController(TeacherService teacherService,
                             FacultyService facultyService,
                             DepartmentService departmentService) {
        this.teacherService = teacherService;
        this.facultyService = facultyService;
        this.departmentService = departmentService;
    }

    @GetMapping()
    public String showAll(Model model) {
        List<Teacher> teachers = teacherService.findAll();

        for (Teacher teacher : teachers) {
            Faculty faculty = facultyService.findByID(teacher.getFaculty().getID());
            Department department = departmentService.findByID(teacher.getDepartment().getID());

            teacher.setFaculty(faculty);
            teacher.setDepartment(department);
        }

        model.addAttribute("teachers", teachers);
        return "/teacher/show-all";
    }

    @GetMapping("/{id}")
    public String showStudent(@PathVariable("id") int id, Model model) {
        model.addAttribute("teacher", teacherService.findByID(id));
        return "teacher/show-teacher";
    }
}
