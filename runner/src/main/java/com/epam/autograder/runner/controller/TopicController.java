package com.epam.autograder.runner.controller;

import com.epam.autograder.runner.bean.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;

/**
 * Topic Controller
 */
@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    @Qualifier("topic")
    private List<Topic> listTopic = Arrays.asList(
            new Topic(0, "Java"),
            new Topic(1, ".NET")
    );

    /**
     * Returns all topics.
     *
     * @return topics list
     */
    @GetMapping
    public List<Topic> getAll() {
        return listTopic;
    }

    /**
     * Returns topic by id.
     *
     * @param id ID of the topic.
     * @return topic
     */
    @GetMapping("/{id}")
    public Topic getTopic(@PathVariable Integer id) {
        return listTopic.get(id);
    }

    /**
     * Add new topic/
     *
     * @param topic Id and description of the topic
     */
    @PostMapping
    public void add(@RequestBody Topic topic) {
        listTopic.add(topic);
    }

}
