package vn.rananu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class BaseController {
    @Autowired
    private MessageSource messageSource;

//    @ExceptionHandler({MethodArgumentNotValidException.class, NotFoundException.class})
//    @ResponseBody
//    public ResponseEntity<?> handleValidationException(Exception ex, Locale locale) {
//        Map<Object, Object> errors  ;
//        ResponseEntity<?> response = null;
//        Result<Object> result;
//        if (ex instanceof MethodArgumentNotValidException) {
//            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
//            errors = bindingResult.getFieldErrors()
//                    .stream()
//                    .collect(Collectors
//                            .toMap(FieldError::getField,
//                                    FieldError::getDefaultMessage));
//
//            result = new Result<>(errors, HttpStatus.BAD_REQUEST.value());
//            response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        }
//        if (ex instanceof NotFoundException) {
//            String message = messageSource.getMessage(ex.getMessage(), new Object[0], locale);
//            result = new Result<>(message, HttpStatus.NOT_FOUND.value());
//            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND    );
//        }
//        return response;
//    }

}
