package com.girevoy.university.controller;

import com.girevoy.university.service.FacultyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
    FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("faculties", facultyService.findAll());
        return "/faculty/show-all";
    }
}
