package com.epam.autograder.runner.entity;

import java.util.Objects;
import java.util.UUID;

public class Sandbox {

    /**
     * Id of the sandbox
     */
    private String id;

    /**
     * Environment id
     */
    private String type;

    /**
     * Link to task
     */
    private String payload;

    /**
     * Current status of the environment
     */
    private SandboxStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public SandboxStatus getStatus() {
        return status;
    }

    public void setStatus(SandboxStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sandbox sandbox = (Sandbox) o;
        if (id != null ? !id.equals(sandbox.id) : sandbox.id != null) return false;
        if (type != null ? !type.equals(sandbox.type) : sandbox.type != null) return false;
        if (payload != null ? !payload.equals(sandbox.payload) : sandbox.payload != null) return false;
        return status == sandbox.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, payload, status);
    }

    @Override
    public String toString() {
        return "Sandbox{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", payload='" + payload + '\'' +
                ", status=" + status +
                '}';
    }
}
