package com.epam.autograder.aqa.entity;

import com.google.gson.annotations.SerializedName;

public class RunnerSubmission {
    @SerializedName("containerId")
    private long containerId;
    @SerializedName("environment")
    private String environment;
    @SerializedName("payload")
    private String payload;

    public RunnerSubmission(long containerId, String environment) {
        this.containerId = containerId;
        this.environment = environment;
    }

    public RunnerSubmission(long containerId, String environment, String payload) {
        this.containerId = containerId;
        this.environment = environment;
        this.payload = payload;
    }

    public long getContainerId() {
        return containerId;
    }

    public void setContainerId(long containerId) {
        this.containerId = containerId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return String.format("RunnerSubmission[containerId = %d, environment = %s, payload = %s]",
                containerId, environment, payload);
    }
}
