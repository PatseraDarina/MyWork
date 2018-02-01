package com.epam.autograder.aqa.entity;

import com.google.gson.annotations.SerializedName;

public class SubmissionEntity {

    @SerializedName("this_id")
    private long id;

    @SerializedName("this_name")
    private String name;

    public SubmissionEntity(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("id = %d\nname = %s", id, name);
    }
}
