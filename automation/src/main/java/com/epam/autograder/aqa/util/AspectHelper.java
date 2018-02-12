package com.epam.autograder.aqa.util;

import com.epam.autograder.aqa.annotation.Step;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;
import java.util.List;

public class AspectHelper {

    private AspectHelper() {
    }

    public static String getMethodNameAndClass(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String methodClass = joinPoint.getSourceLocation().getWithinType().getSimpleName();
        return String.format("%s:%s", methodClass, methodName);

    }

    public static String getMethodArguments(JoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        if (params.length == 0) {
            return "[]";
        } else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String[] paramsNames = signature.getParameterNames();
            List<String> result = new ArrayList<>(params.length);
            for (int i = 0; i < params.length; i++) {
                result.add(String.format("%s: %s", paramsNames[i], String.valueOf(params[i])));
            }
            return result.toString();
        }
    }

    public static String getStepValue(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(Step.class).value();
    }
}
