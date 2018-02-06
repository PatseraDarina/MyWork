package com.epam.autograder.core.exception;

/**
 * Class represents abstract business exception.
 *
 * @author Eduard Khachirov
 */
public class BusinessException extends RuntimeException {

    /**
     * Create instance of BusinessException
     *
     * @param message the detail message
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Create instance of BusinessException
     *
     * @param cause   exception cause
     * @param message the exception detail message
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
