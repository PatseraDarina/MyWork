package com.epam.autograder.aqa.entity;

import com.google.gson.annotations.SerializedName;

public class Submission {

    @SerializedName("submissionId")
    private long id;

    @SerializedName("environmentId")
    private String envId;

    @SerializedName("inputSource")
    private String inputSource;

    @SerializedName("inputData")
    private String inputData;

    @SerializedName("status")
    private String status;

    public Submission(String envId, String inputSource, String inputData) {
        this.envId = envId;
        this.inputSource = inputSource;
        this.inputData = inputData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEnvId() {
        return envId;
    }

    public void setEnvId(String envId) {
        this.envId = envId;
    }

    public String getInputSource() {
        return inputSource;
    }

    public void setInputSource(String inputSource) {
        this.inputSource = inputSource;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Submission[id = %d, environmentId = %s, inputSource = %s, inputData = %s, status = %s]",
                id, envId, inputSource, inputData, status);
    }
}
