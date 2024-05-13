package org.shanthan.customerdataservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("@annotation(org.shanthan.customerdataservice.annotation.LogExecutionTime)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            log.info("Method {} execution started", methodName);
            result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("Method {} execution finished in {} ms", methodName, elapsedTime);
        } catch (Exception e) {
            log.error("Method {} execution failed due to this reason -> {} ", methodName, e.getMessage(), e);
            throw e;
        }
        return result;
    }
}
