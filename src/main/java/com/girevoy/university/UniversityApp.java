package com.girevoy.university;

import com.girevoy.university.dao.StudentDAO;
import com.girevoy.university.model.entity.Student;
import com.girevoy.university.spring.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UniversityApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        StudentDAO studentDAO = context.getBean("studentDAO", StudentDAO.class);

        Student student = studentDAO.findByID(1).orElse(new Student());

        System.out.println(student.toString());
    }
}
