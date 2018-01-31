package com.epam.autograder.controller;

import com.epam.autograder.entity.Topic;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TopicController {

    private List<Topic> topics = new ArrayList<>(Arrays.asList(
            new Topic("0","Spring Framework"),
            new Topic("1", "Java Core")
    ));

    @RequestMapping("/topics")
    public List<Topic> getAllTopics() {
        return topics;
    }

    /**
     * get topic by id
     *
     * @param id environment Id
     * @return Topic topic
     */
    @RequestMapping("/topics/{id}")
    public Topic getTopic(@PathVariable String id) {
        return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }
}
