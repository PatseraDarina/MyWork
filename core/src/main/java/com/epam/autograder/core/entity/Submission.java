package com.epam.autograder.core.entity;

import java.util.Objects;

/**
 * Represents a students homework submission.
 *
 * @author Valeriia Chub
 */
public class Submission {

    /**
     * Submission id
     */
    private long submissionId;

    /**
     * Environment id
     */
    private String environmentId;
    /**
     * Type of input source
     */
    private InputSource inputSource;
    /**
     * Input data
     */
    private String inputData;

    /**
     * @return current submission id
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * @param submissionId sets submission id
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * @return current environment id
     */
    public String getEnvironmentId() {
        return environmentId;
    }

    /**
     * @param environmentId sets environment id
     */
    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    /**
     * @return current type of input source
     */
    public InputSource getInputSource() {
        return inputSource;
    }

    /**
     * @param inputSource sets type of input source
     */
    public void setInputSource(InputSource inputSource) {
        this.inputSource = inputSource;
    }

    /**
     * @return current input data
     */
    public String getInputData() {
        return inputData;
    }

    /**
     * @param inputData sets input data
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

    @Override
    public String toString() {
        return "Submission{"
                + "submissionId=" + submissionId
                + ", environmentId='" + environmentId
                + '\''
                + ", inputSource=" + inputSource
                + ", inputData='" + inputData + '\''
                + '}';
    }
}
