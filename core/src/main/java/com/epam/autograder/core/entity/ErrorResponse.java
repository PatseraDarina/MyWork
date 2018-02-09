package com.epam.autograder.core.entity;

/**
 * Entity that will be sent if some problems in application  occur
 * <p>
 * When exception occurs it goes to ExceptionHandlingController and then  ErrorResponse will be constructed and send to consumer
 *
 * @author EduardKhachirov
 */
public class ErrorResponse {
    private int statusCode;
    private String statusName;
    private String description;

    /**
     * create ErrorResponse instance
     *
     * @param statusCode  reserved HTTP status code
     * @param statusName  reserved HTTP status
     * @param description an error message from exception that was occurred
     */
    public ErrorResponse(int statusCode, String statusName, String description) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.description = description;
    }

    /**
     * statusCode describes HTTP status code
     *
     * @return statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * statusName describes HTTP  status name
     *
     * @return statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * description is error message  of occurred exception
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }
}
