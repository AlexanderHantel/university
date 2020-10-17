package com.girevoy.university.dao.logger;

import com.girevoy.university.dao.DepartmentDAO;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

public class TestDepartmentDAOLogger {

    @Mock
    JdbcTemplate jdbcTemplateMock;

    @Mock
    DataSource dataSourceMock;

    @Mock
    SimpleJdbcInsert simpleJdbcInsertMock;

    @InjectMocks
    DepartmentDAO departmentDAO;

    @BeforeEach
    public void init_mocks() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(departmentDAO, "jdbcTemplate", jdbcTemplateMock);
        ReflectionTestUtils.setField(departmentDAO, "simpleJdbcInsert", simpleJdbcInsertMock);
    }
}
