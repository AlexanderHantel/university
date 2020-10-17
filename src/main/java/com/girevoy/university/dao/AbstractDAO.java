package com.girevoy.university.dao;

import com.girevoy.university.model.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;

import static java.lang.String.format;

public abstract class AbstractDAO<T extends Entity> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String tableName;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private static final String FIND_BY_ID_SQL = "SELECT * FROM %s WHERE id=?;";
    private static final String FIND_ALL_SQL = "SELECT * FROM %s;";
    private static final String DELETE_SQL = "DELETE FROM %s WHERE id = ?;";

    public AbstractDAO(DataSource dataSource, String tableName) {
        this.tableName = tableName;
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(tableName)
                                                           .usingGeneratedKeyColumns("id");
    }

    public T insert(T t) {
        Map<String, Object> parameters = getInsertedParameters(t);

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        getLogger().debug("Object {} ID = {} was inserted", tableName, id);

        t.setID((long) id);
        return t;
    }

    public Optional<T> findByID(long id) {
        Optional<T> object = Optional.empty();

        try {
            object = Optional.of(jdbcTemplate.queryForObject(format(FIND_BY_ID_SQL, tableName),
                                                             new Object[] {id},
                                                             getMapper()));
            getLogger().debug("Object of {} ID = {} was found", tableName, id);
        } catch (EmptyResultDataAccessException e) {
            getLogger().error(format("Object of %s ID = %d has not been found", tableName, id), e);
        }

        return object;
    }

    public List<T> findAll() {
        List<T> list = jdbcTemplate.query(format(FIND_ALL_SQL, tableName), getMapper());
        getLogger().debug("{} entities were found", list.size());

        return list;
    }

    public abstract boolean update(T t);

    public boolean delete(long id) {
        int rows = jdbcTemplate.update(format(DELETE_SQL, tableName), id);
        getLogger().debug("{} rows were deleted", rows);

        return rows > 0;
    }

    public abstract Map<String, Object> getInsertedParameters(T t);

    public abstract RowMapper<T> getMapper();

    public String getTableName() {
        return tableName;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    protected abstract Logger getLogger();
}
