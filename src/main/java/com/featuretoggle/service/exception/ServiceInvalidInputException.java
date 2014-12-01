package com.featuretoggle.service.exception;

import java.util.List;

public class ServiceInvalidInputException extends ServiceException {
    public ServiceInvalidInputException(String message, List<String> errors) {
        super(message, errors);
    }
}
