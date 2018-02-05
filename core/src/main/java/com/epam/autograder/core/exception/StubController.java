package com.epam.autograder.core.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Stub controller for testing
 * <p>
 * TO BE removed
 */
@Controller
public class StubController {

    /**
     * emulate incorrect working in order to test ExceptionController
     */
    @RequestMapping("/businessError")
    public void throwBusinessException() {
        throw new BusinessException("BusinessException");
    }

    /**
     * emulate incorrect working in order to test ExceptionController
     */
    @RequestMapping("/runtimeError")
    public void throwRuntimeException() {
        throw new RuntimeException("RuntimeException");
    }

}
