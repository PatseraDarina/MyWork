package com.epam.autograder.controller;

import com.epam.autograder.entity.Topic;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * simple stub controller
 */
@RestController
public class TopicController {

    /**
     *
     */
    private List<Topic> topics;

    /**
     * default constructor
     */
    public TopicController() {
        topics = new ArrayList<>();
        topics.add(new Topic("0", "Spring Framework"));
        topics.add(new Topic("1", "Java Core"));
    }

    /**
     * get all topics
     *
     * @return List list of Topics
     */
    @RequestMapping("/topics")
    public List<Topic> getAllTopics() {
        return topics;
    }

    /**
     * get topic by id
     *
     * @param id environment Id3333
     * @return Topic topic
     */
    @RequestMapping("/topics/{id}")
    public Topic getTopic(@PathVariable String id) {
        return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }
}
