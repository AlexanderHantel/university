package com.girevoy.university.spring.config;

import com.girevoy.university.dao.*;
import com.girevoy.university.service.impl.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan("com.girevoy.university")
@PropertySource("classpath:database.properties")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final Environment environment;

    private static final String URL = "url";
    private static final String USER = "user";
    private static final String DRIVER = "driver";
    private static final String PASSWORD = "password";

    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty(URL));
        driverManagerDataSource.setUsername(environment.getProperty(USER));
        driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
        driverManagerDataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty(DRIVER)));
        return driverManagerDataSource;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/view/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
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

