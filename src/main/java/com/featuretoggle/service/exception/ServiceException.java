package com.featuretoggle.service.exception;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class ServiceException extends Exception {
    private Set<String> errors;

    public ServiceException(String message, List<String> errors) {
        this(message, null, errors);
    }

    public ServiceException(String message, Exception cause, List<String> errors) {
        super(message, cause);
        this.errors = new LinkedHashSet<>();
        this.errors.addAll(errors);
    }

    public Set<String> getErrorMessages() {
        return errors;
    }
}
