package com.epam.autograder.core.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long submissionId;
    private String environmentId;
    private InputSource inputSource;
    private String inputData;

    public long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public InputSource getInputSource() {
        return inputSource;
    }

    public void setInputSource(InputSource inputSource) {
        this.inputSource = inputSource;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    @Override
    public int hashCode() {

        return Objects.hash(submissionId, environmentId, inputSource, inputData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission that = (Submission) o;
        return submissionId == that.submissionId &&
                Objects.equals(environmentId, that.environmentId) &&
                inputSource == that.inputSource &&
                Objects.equals(inputData, that.inputData);
    }

    @Override
    public String toString() {
        return "Submission{" +
                "submissionId=" + submissionId +
                ", environmentId='" + environmentId + '\'' +
                ", inputSource=" + inputSource +
                ", inputData='" + inputData + '\'' +
                '}';
    }
}
