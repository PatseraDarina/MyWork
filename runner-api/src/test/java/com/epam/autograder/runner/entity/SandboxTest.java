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
    private static final String TYPE = "type";
    private static final String ID = "idContainer";
    private static final String ID_WITH_SPACE = "id Container";
    private static final String ERROR_ID_NULL_MASSAGE = "Id cannot be null or empty";
    private static final String ERROR_ID_SPACE_MASSAGE = "Spaces are not allowed";
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
        sandbox.setPayload(PAYLOAD);
        sandbox.setType(TYPE);
        sandbox.setStatus(SandboxStatus.COMPLETE);
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Tests of set NULL in Id parameter of Sandbox.
     */
    @Test
    public void shouldReturnErrorIdCannotBeNull() {
        sandbox.setId(null);
        constraintViolations = validator.validate(sandbox);
        assertEquals(1, constraintViolations.size());
        assertEquals(ERROR_ID_NULL_MASSAGE, constraintViolations.iterator().next().getMessage());
    }

    /**
     * Tests of set SPACE in Id parameter  of Sandbox.
     */
    @Test
    public void shouldReturnErrorIdCannotContainsSpace() {
        sandbox.setId(ID_WITH_SPACE);
        constraintViolations = validator.validate(sandbox);
        assertEquals(1, constraintViolations.size());
        assertEquals(ERROR_ID_SPACE_MASSAGE, constraintViolations.iterator().next().getMessage());
    }

    /**
     * Tests of set parameters of Sandbox.
     */
    @Test
    public void shouldNotContainsError() {
        sandbox.setId(ID);
        constraintViolations = validator.validate(sandbox);
        assertEquals(0, constraintViolations.size());
    }
}
