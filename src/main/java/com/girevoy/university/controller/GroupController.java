package com.girevoy.university.controller;

import com.girevoy.university.model.entity.Faculty;
import com.girevoy.university.model.entity.Group;
import com.girevoy.university.service.FacultyService;
import com.girevoy.university.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/group")
public class GroupController {
    GroupService groupService;
    FacultyService facultyService;

    public GroupController(GroupService groupService, FacultyService facultyService) {
        this.groupService = groupService;
        this.facultyService = facultyService;
    }

    @GetMapping()
    public String showAll(Model model) {
        Map<Group, Long> groups = groupService.findAllWithStudentsNumber();

        groups.forEach((k, v) -> {
            Faculty faculty = facultyService.findByID(k.getFaculty().getID());
            k.setFaculty(faculty);
        });

        model.addAttribute("groups", groups);
        return "/group/show-all";
    }
}
