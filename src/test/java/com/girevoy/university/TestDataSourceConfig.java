package com.girevoy.university;

import com.girevoy.university.dao.*;
import com.girevoy.university.service.impl.*;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.girevoy.university")
@PropertySource("classpath:database.properties")
public class TestDataSourceConfig {

    public TestDataSourceConfig() {
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                                            .addScript("classpath:createTables.sql")
                                            .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DepartmentDAO departmentDAO() {
        return new DepartmentDAO(dataSource());
    }

    @Bean
    public DepartmentServiceImpl departmentService() {
        return new DepartmentServiceImpl(departmentDAO());
    }

    @Bean
    public FacultyDAO facultyDAO() {
        return new FacultyDAO(dataSource());
    }

    @Bean
    public FacultyServiceImpl facultyService() {
        return new FacultyServiceImpl(facultyDAO());
    }

    @Bean
    public GroupDAO groupDAO() {
        return new GroupDAO(dataSource());
    }

    @Bean
    public GroupServiceImpl groupService() {
        return new GroupServiceImpl(groupDAO());
    }

    @Bean
    public LessonDAO lessonDAO() {
        return new LessonDAO(dataSource());
    }

    @Bean
    public LessonServiceImpl lessonService() {
        return new LessonServiceImpl(lessonDAO());
    }

    @Bean
    public RoomDAO roomDAO() {
        return new RoomDAO(dataSource());
    }

    @Bean
    public RoomServiceImpl roomService() {
        return new RoomServiceImpl(roomDAO());
    }

    @Bean
    public StudentDAO studentDAO() {
        return new StudentDAO(dataSource());
    }

    @Bean
    public StudentServiceImpl studentService() {
        return new StudentServiceImpl(studentDAO());
    }

    @Bean
    public SubjectDAO subjectDAO() {
        return new SubjectDAO(dataSource());
    }

    @Bean
    public SubjectServiceImpl subjectService() {
        return new SubjectServiceImpl(subjectDAO());
    }

    @Bean
    public TeacherDAO teacherDAO() {
        return new TeacherDAO(dataSource());
    }

    @Bean
    public TeacherServiceImpl teacherService() {
        return new TeacherServiceImpl(teacherDAO());
    }

    @Bean
    public ThemeDAO themeDAO() {
        return new ThemeDAO(dataSource());
    }

    @Bean
    public ThemeServiceImpl themeService() {
        return new ThemeServiceImpl(themeDAO());
    }

    @Bean
    public UniversityDAO universityDAO() {
        return new UniversityDAO(dataSource());
    }

    @Bean
    public UniversityServiceImpl universityService() {
        return new UniversityServiceImpl(universityDAO());
    }

    @Bean
    public TimetableServiceImpl timetableService() {
        return new TimetableServiceImpl(lessonDAO());
    }
}
