package com.epam.autograder.runner.entity;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Tests Sandbox entity.
 */
public class SandboxTest {
    private static final String PAYLOAD = "payload";
    private static final String TYPE = "hello-world";
    private static final String ID = "111-AAA-bbb";
    private static final String ID_WRONG = "111,AAA-bbb";
    private static final String ERROR_MASSAGE_ID_NULL = "Id cannot be null or empty";
    private static final String ERROR_MASSAGE_ID_FORMAT = "Wrong id format";
    private static final String ERROR_MASSAGE_TYPE = "Type cannot be null or empty";
    private static final String ERROR_MASSAGE_ID_PAYLOAD = "Payload cannot be null or empty";
    private static final String ERROR_MASSAGE_ID_STATUS = "Status cannot be null";
    private static final int ERROR_COUNT = 4;
    private static final int ONE_ERROR = 1;
    private Sandbox sandbox;
    private ValidatorFactory factory;
    private Validator validator;
    private Set<ConstraintViolation<Sandbox>> constraintViolations;

    /**
     * Initial Sandbox ,ValidatorFactory, Validator for tests.
     */
    @Before
    public void setUp() {
        sandbox = new Sandbox();
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Tests of set NULL in Id parameter of Sandbox.
     */
    @Test
    public void shouldReturnErrorIdCannotBeNull() {
        sandbox.setPayload(PAYLOAD);
        sandbox.setType(TYPE);
        sandbox.setStatus(SandboxStatus.COMPLETE);
        sandbox.setId(null);
        constraintViolations = validator.validate(sandbox);
        assertEquals(ONE_ERROR, constraintViolations.size());
        assertEquals(ERROR_MASSAGE_ID_NULL, constraintViolations.iterator().next().getMessage());
    }

    /**
     * Tests of set in Id wrong parameter of Sandbox.
     */
    @Test
    public void shouldReturnErrorIdByPattern() {
        sandbox.setPayload(PAYLOAD);
        sandbox.setType(TYPE);
        sandbox.setStatus(SandboxStatus.COMPLETE);
        sandbox.setId(ID_WRONG);
        constraintViolations = validator.validate(sandbox);
        assertEquals(ONE_ERROR, constraintViolations.size());
        assertEquals(ERROR_MASSAGE_ID_FORMAT, constraintViolations.iterator().next().getMessage());
    }
    /**
     * Tests of set NULL in Type parameter of Sandbox.
     */
    @Test
    public void shouldReturnErrorNullType() {
        sandbox.setPayload(PAYLOAD);
        sandbox.setType(null);
        sandbox.setStatus(SandboxStatus.COMPLETE);
        sandbox.setId(ID);
        constraintViolations = validator.validate(sandbox);
        assertEquals(ONE_ERROR, constraintViolations.size());
        assertEquals(ERROR_MASSAGE_TYPE, constraintViolations.iterator().next().getMessage());
    }
    /**
     * Tests of set NULL in Payload parameter of Sandbox.
     */
    @Test
    public void shouldReturnErrorNullPayload() {
        sandbox.setPayload(null);
        sandbox.setType(TYPE);
        sandbox.setStatus(SandboxStatus.COMPLETE);
        sandbox.setId(ID);
        constraintViolations = validator.validate(sandbox);
        assertEquals(ONE_ERROR, constraintViolations.size());
        assertEquals(ERROR_MASSAGE_ID_PAYLOAD, constraintViolations.iterator().next().getMessage());
    }
    /**
     * Tests of set NULL in SandboxStatus parameter of Sandbox.
     */
    @Test
    public void shouldReturnErrorNullSandboxStatus() {
        sandbox.setPayload(PAYLOAD);
        sandbox.setType(TYPE);
        sandbox.setStatus(null);
        sandbox.setId(ID);
        constraintViolations = validator.validate(sandbox);
        assertEquals(ONE_ERROR, constraintViolations.size());
        assertEquals(ERROR_MASSAGE_ID_STATUS, constraintViolations.iterator().next().getMessage());
    }

    /**
     * Tests of set all parameters of Sandbox.
     */
    @Test
    public void shouldNotContainsError() {
        sandbox.setPayload(PAYLOAD);
        sandbox.setType(TYPE);
        sandbox.setStatus(SandboxStatus.COMPLETE);
        sandbox.setId(ID);
        constraintViolations = validator.validate(sandbox);
        assertEquals(0, constraintViolations.size());
    }

    /**
     * Tests of set wrong parameters of Sandbox.
     */
    @Test
    public void shouldReturnErrorInAllSandboxParameters() {
        sandbox.setPayload(null);
        sandbox.setType(null);
        sandbox.setStatus(null);
        sandbox.setId(null);
        constraintViolations = validator.validate(sandbox);
        assertEquals(ERROR_COUNT, constraintViolations.size());
    }
}
