package com.epam.autograder.runner.entity;

import java.util.Objects;

/**
 * Represents a students homework submission.
 */
public class Submission {

    /**
     * Task id
     */
    private long submissionId;

    /**
     * Image id which container will create
     */
    private String environmentId;

    /**
     * Link to task
     */
    private String payload;

    /**
     * @return task id
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * @param submissionId task id
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * @return image id which container will create
     */
    public String getEnvironmentId() {
        return environmentId;
    }

    /**
     * @param environmentId image id which container will create
     */
    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }


    /**
     * @return link to task
     */
    public String getPayload() {
        return payload;
    }

    /**
     * @param payload link to task
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Submission that = (Submission) o;
        return submissionId == that.submissionId
                && Objects.equals(environmentId, that.environmentId)
                && Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(submissionId, environmentId, payload);
    }
}
