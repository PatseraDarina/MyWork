package com.epam.autograder.runner.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents a students homework submission.
 */
public class Submission {

    private long submissionId;
    private String environmentId;
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

        return new EqualsBuilder()
                .append(submissionId, that.submissionId)
                .append(environmentId, that.environmentId)
                .append(payload, that.payload)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(submissionId)
                .append(environmentId)
                .append(payload)
                .toHashCode();
    }
}
