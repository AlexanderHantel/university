package com.girevoy.university.service;

import com.girevoy.university.TestDataSourceConfig;
import com.girevoy.university.model.entity.Department;
import com.girevoy.university.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestDataSourceConfig.class)
@Sql({"/createTables.sql", "/test-data.sql"})
@WebAppConfiguration
public class TestDepartmentService {
    @Autowired
    private ApplicationContext context;
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    public void init() {
        departmentService = context.getBean("departmentService", DepartmentServiceImpl.class);
    }

    @Test
    public void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(context);
    }

    @Test
    public void insert_ShouldInsertDepartmentAndGenerateID(){
        Department expectedDepartment = new Department();
        expectedDepartment.setName("Test");

        expectedDepartment = departmentService.insert(expectedDepartment);

        Department actualDepartment = departmentService.findByID(5);
        assertEquals(expectedDepartment, actualDepartment);
    }
}
