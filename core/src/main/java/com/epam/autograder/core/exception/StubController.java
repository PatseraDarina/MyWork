package com.epam.autograder.core.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Stub interface for testing
 * <p>
 * TO BE removed
 */
@Controller
public interface StubController {
    /**
     * emulate incorrect working in order to test ExceptionController
     */
    @RequestMapping("/throwException")
    String handleRequest();

}
