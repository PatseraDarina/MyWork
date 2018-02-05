package com.epam.autograder.runner.bean;

import org.springframework.stereotype.Component;

@Component("topic")
public class Topic {

    /**
     * ID of the Topic
     */
    private Integer id;

    /**
     * Name of the Topic
     */
    private String name;

    public Topic() {
    }

    public Topic(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
