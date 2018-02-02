package com.epam.autograder.core.exception.handler;

import com.epam.autograder.core.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final String DEFAULT_ERROR_DESCRIPTION = "ops...looks like something went wrong";

    private class ResponseBody {
        private int statusCode;
        private String statusName;
        private String description;

        private ResponseBody(int statusCode, String statusName, String description) {
            this.statusCode = statusCode;
            this.statusName = statusName;
            this.description = description;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getStatusName() {
            return statusName;
        }

        public String getDescription() {
            return description;
        }

    }

    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<ResponseBody> handleBusinessException(BusinessException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return buildResponseEntity(exception, httpStatus);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ResponseBody> handleInternalServerException(RuntimeException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return buildResponseEntity(exception, httpStatus);
    }

    private ResponseEntity<ResponseBody> buildResponseEntity(Throwable exception, HttpStatus httpStatus) {
        String description = exception.getMessage();
        if (description == null) {
            description = DEFAULT_ERROR_DESCRIPTION;
        }
        ResponseBody body = new ResponseBody(httpStatus.value(), httpStatus.name(), description);
        return new ResponseEntity<>(body, httpStatus);
    }

}
