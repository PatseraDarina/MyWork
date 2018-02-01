package com.epam.autograder.runner.entity;

import java.util.Objects;

/**
 * Represents a students homework submission.
 *
 */
public class Submission {

    private long submissionId;
    private String environmentId;
    private InputSource inputSource;
    private String inputData;

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
     * @return user task type
     */
    public InputSource getInputSource() {
        return inputSource;
    }

    /**
     * @param inputSource user task type
     */
    public void setInputSource(InputSource inputSource) {
        this.inputSource = inputSource;
    }

    /**
     * @return link to task
     */
    public String getInputData() {
        return inputData;
    }

    /**
     * @param inputData link to task
     */
    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    @Override
    public int hashCode() {

        return Objects.hash(submissionId, environmentId, inputSource, inputData);
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
                && inputSource == that.inputSource
                && Objects.equals(inputData, that.inputData);
    }


}
