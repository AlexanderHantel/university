package com.girevoy.university.exception.service;

public class EntityNotDeletedException extends ServiceException {
    public EntityNotDeletedException(String message) {
        super(message);
    }
}
