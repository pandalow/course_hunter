package com.hunt.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * logbook entry
 */
@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class LogAspect {

    private final HttpServletRequest request;
    @Around("@annotation(com.hunt.anno.Log)")
    public Object recordLog(ProceedingJoinPoint jointPoint) throws Throwable {
        //TODO Adding Log injection function

        return null;
    }
}
