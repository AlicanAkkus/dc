package com.aakkus.dc.aspect;

import com.aakkus.dc.exception.ClickableMethodCouldNotExecuted;
import com.aakkus.dc.annotation.Clickable;
import com.aakkus.dc.enums.ClickableAction;
import com.aakkus.dc.service.ClickableExpirationService;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class ClickableAspect {

    @Autowired
    ClickableExpirationService clickableExpirationService;

    @Around("@annotation(com.aakkus.dc.annotation.Clickable) && args(request, ..)")
    public Object clickable(ProceedingJoinPoint joinPoint, Object request) throws Throwable {
        Clickable clickable = retrieveClickable(joinPoint);

        if (clickableExpirationService.isExpried(request.toString())) {
            putNewClickableToCache(request, clickable);

            return joinPoint.proceed();
        }

        return doClickableAction(joinPoint, clickable);
    }

    private Object doClickableAction(ProceedingJoinPoint joinPoint, Clickable clickable) throws Throwable {
        if (ClickableAction.DOTHROW.equals(clickable.action())) {
            throw new ClickableMethodCouldNotExecuted(clickable.message());
        } else {
            return joinPoint.proceed();
        }
    }

    private void putNewClickableToCache(Object request, Clickable clickable) {
        Long puttingTime = System.currentTimeMillis();

        Pair<Long, Pair<Long, Object>> clickableRequest = Pair.of(puttingTime, Pair.of(clickable.inMilliSeconds(), request));
        clickableExpirationService.put(request.toString(), clickableRequest);
    }

    private Clickable retrieveClickable(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
        Method method = methodSignature.getMethod();

        return method.getDeclaredAnnotation(Clickable.class);
    }
}
