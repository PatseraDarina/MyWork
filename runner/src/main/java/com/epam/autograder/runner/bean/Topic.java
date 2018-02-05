package com.epam.autograder.runner.bean;

import org.springframework.stereotype.Component;

/**
 * Stub class for test.
 */
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

    /**
     * Constructor
     */
    public Topic() {
    }

    /**
     *  Constructor of the topic
     * @param id topic id
     * @param name topic name
     */
    public Topic(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return id of topic
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id id of topic
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name of topic
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name of topic
     */
    public void setName(String name) {
        this.name = name;
    }

}
