package com.epam.autograder.core.entity;

/**
 * simple stub entity
 */
public class Topic {
    /**
     * The id.
     */
    private String id;
    /**
     * The name.
     */
    private String name;

    /**
     * constructor with parameters
     *
     * @param id topic id
     * @param name topic name
     */
    public Topic(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * get topic id
     *
     * @return topic String
     */
    public String getId() {
        return id;
    }

    /**
     * set topic id
     *
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get topic name
     *
     * @return topic String
     */
    public String getName() {
        return name;
    }

    /**
     * set topic id
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

}
