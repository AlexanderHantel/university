package com.girevoy.university.controller;

import com.girevoy.university.model.entity.Department;
import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.service.DepartmentService;
import com.girevoy.university.service.FacultyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    DepartmentService departmentService;
    FacultyService facultyService;

    public DepartmentController(DepartmentService departmentService, FacultyService facultyService) {
        this.departmentService = departmentService;
        this.facultyService = facultyService;
    }

    @GetMapping()
    public String showAll(Model model) {
        List<Department> departments = departmentService.findAll();
        for (Department department : departments) {
            Faculty faculty = facultyService.findByID(department.getFaculty().getID());
            department.setFaculty(faculty);
        }
        model.addAttribute("departments", departments);
        return "/department/show-all";
    }
}
