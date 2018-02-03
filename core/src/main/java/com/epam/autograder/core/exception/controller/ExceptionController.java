package com.epam.autograder.core.exception.controller;

import com.epam.autograder.core.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class provides cross-cutting exception handling for all application controllers
 *
 * @author Eduard Khachirov
 */

@ControllerAdvice
public class ExceptionController {

    private static final String DEFAULT_ERROR_DESCRIPTION = "Oops...looks like something went wrong";

    /**
     * DTO which provides information about exception which  will be sent as response in http body.
     */
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

    /**
     * Handle BusinessExceptions and send response with HTTP Bad Request(400) status
     *
     * @param exception exception that occurred in application
     * @return ResponseEntity object with ResponseBody DTO and http status
     * @see ResponseBody
     */
    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<ResponseBody> handleBusinessException(BusinessException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return buildResponseEntity(exception, httpStatus);
    }

    /**
     * Handle RuntimeExceptions and send response with HTTP Internal server error(500) status
     *
     * @param exception exception that occurred in application
     * @return ResponseEntity object with ResponseBody DTO and http status
     * @see ResponseBody
     */
    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ResponseBody> handleInternalServerException(RuntimeException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return buildResponseEntity(exception, httpStatus);
    }

    /**
     * Build ResponseEntity for response.
     * If exception does not provide message  description will be set by default value
     *
     * @param exception  exception that occurred in application
     * @param httpStatus status for response
     * @return ResponseEntity object with ResponseBody DTO and http status
     */
    private ResponseEntity<ResponseBody> buildResponseEntity(Throwable exception, HttpStatus httpStatus) {
        String description = exception.getMessage();
        if (description == null) {
            description = DEFAULT_ERROR_DESCRIPTION;
        }
        ResponseBody body = new ResponseBody(httpStatus.value(), httpStatus.name(), description);
        return new ResponseEntity<>(body, httpStatus);
    }

}
