package com.girevoy.university.service.impl;

import com.girevoy.university.dao.AbstractDAO;
import com.girevoy.university.exception.service.EntityNotDeletedException;
import com.girevoy.university.exception.service.EntityNotUpdatedException;
import com.girevoy.university.model.entity.Entity;
import com.girevoy.university.service.Service;
import org.slf4j.Logger;

import java.util.List;

import static java.lang.String.format;

public abstract class AbstractServiceImpl<T extends Entity> implements Service<T> {
    private final AbstractDAO<T> dao;

    public AbstractServiceImpl(AbstractDAO<T> dao) {
        this.dao = dao;
    }

    public AbstractDAO<T> getDao() {
        return dao;
    }

    @Override
    public T insert(T t) {
        return dao.insert(t);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public void update(T t) {
        if (!dao.update(t)) {
            String message = format("Object of %s ID = %d has not been updated",
                                           getObjectSimpleName(), t.getID());
            getLogger().error(message);
            throw new EntityNotUpdatedException(message);
        }
    }

    @Override
    public void delete(long id) {
        if (!dao.delete(id)) {
            String message = format("Object of %s ID = %d has not been deleted", getObjectSimpleName(), id);
            getLogger().error(message);
            throw new EntityNotDeletedException(message);
        }
    }

    protected abstract Logger getLogger();
    protected abstract String getObjectSimpleName();
}
