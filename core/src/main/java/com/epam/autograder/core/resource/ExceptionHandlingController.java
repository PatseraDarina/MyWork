package com.epam.autograder.core.resource;

import com.epam.autograder.core.dto.ErrorResponseDto;
import com.epam.autograder.core.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.util.Optional.ofNullable;

/**
 * Class provides cross-cutting exception handling for all application controllers
 *
 * @author Eduard Khachirov
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlingController {

    public static final String DEFAULT_ERROR_DESCRIPTION = "Oops...looks like something went wrong";

    /**
     * Handle BusinessExceptions and send response with HTTP Bad Request(400) status and description of problem.
     *
     * @param exception exception that occurred in application
     * @return ErrorResponse entity
     * @see ErrorResponseDto
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BusinessException.class, HttpMessageNotReadableException.class})
    protected ErrorResponseDto handleBusinessException(BusinessException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle RuntimeExceptions and send response with HTTP Internal server error(500) status and description of problem.
     *
     * @param exception exception that occurred in application
     * @return ErrorResponse entity
     * @see ErrorResponseDto
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    protected ErrorResponseDto handleInternalServerException(RuntimeException exception) {
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Build ErrorResponse entity for response.
     * If exception does not provide message  then description will be set by default value
     *
     * @param exception  exception that occurred in application
     * @param httpStatus status for response
     * @return ErrorResponse entity
     */
    private ErrorResponseDto buildErrorResponse(Throwable exception, HttpStatus httpStatus) {
        String description = ofNullable(exception.getMessage()).orElse(DEFAULT_ERROR_DESCRIPTION);
        return new ErrorResponseDto(httpStatus.value(), httpStatus.name(), description);
    }

}
