package com.girevoy.university.controller;

import com.girevoy.university.service.UniversityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UniversityController {
    UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping()
    public String hello(Model model) {
        model.addAttribute("university", universityService.findByID(1));
        return "/index";
    }
}
