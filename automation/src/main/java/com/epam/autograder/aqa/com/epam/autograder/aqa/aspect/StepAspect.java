package com.epam.autograder.aqa.com.epam.autograder.aqa.aspect;

import com.epam.autograder.aqa.util.AspectHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.testng.Reporter;

@Aspect
public class StepAspect {

    @Pointcut("@annotation(com.epam.autograder.aqa.annotation.Step)")
    public void withStepAnnotation() {
    }

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
    }

    @Before("anyMethod() && withStepAnnotation()")
    public void beforeInvocationStep(final JoinPoint joinPoint) {
        String annotationValue = AspectHelper.getStepValue(joinPoint);
        String name = AspectHelper.getMethodNameAndClass(joinPoint);
        String printedName = annotationValue.isEmpty() ? name : annotationValue;
        String params = AspectHelper.getMethodArguments(joinPoint);
        Reporter.log(String.format("RUN > [%s] with Args: %s", printedName, params));
    }
}
