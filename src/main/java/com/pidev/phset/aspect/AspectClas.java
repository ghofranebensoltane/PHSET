package com.pidev.phset.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AspectClas {
    @Around("execution(* com.pidev.phset.services.*.add*(..))")
    public void performaneCal(ProceedingJoinPoint pjp) throws Throwable{
        Long startDate=System.currentTimeMillis();
        pjp.proceed();
        Long differenceTime=System.currentTimeMillis() - startDate;
        log.info("la methode a éxécuté   " +differenceTime);
    }

}