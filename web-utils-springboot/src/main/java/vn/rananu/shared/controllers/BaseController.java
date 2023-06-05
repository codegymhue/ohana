package vn.rananu.shared.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import vn.rananu.shared.exceptions.BaseException;

import java.util.Map;

public abstract class BaseController {
    @Autowired
    private MessageSource messageSource;

    public void addError(BindingResult bindingResult, Exception exception) {
        if (exception instanceof BaseException) {
            BaseException baseException = ((BaseException) exception);
            Map<String, String> errors = baseException.getErrors();
            if (errors != null)
                errors.forEach(bindingResult::rejectValue);
            String message = baseException.getMessage();
            if (message != null)
                bindingResult.reject(message);
        }


    }
}
