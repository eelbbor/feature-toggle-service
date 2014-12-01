package com.featuretoggle.service.exception;

import java.util.List;

public class ServicePersistenceException extends ServiceException {

    public ServicePersistenceException(String message, List<String> errors) {
        super(message, errors);
    }

    public ServicePersistenceException(String message, Exception cause, List<String> errors) {
        super(message, cause, errors);
    }
}
