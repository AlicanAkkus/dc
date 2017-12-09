package com.aakkus.dc.aspect;

import com.aakkus.dc.annotation.PreventDoubleClick;
import com.aakkus.dc.enums.PreventDoubleClickAction;
import com.aakkus.dc.exception.PreventDoubleClickMethodCouldNotExecuted;
import com.aakkus.dc.service.PreventDoubleClickService;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;


@Aspect
@Component
public class PreventDoubleClickAspect {

    @Autowired
    PreventDoubleClickService preventDoubleClickService;

    @Around("@annotation(com.aakkus.dc.annotation.PreventDoubleClick)")
    public Object clickable(ProceedingJoinPoint joinPoint) throws Throwable {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        String methodSignature = joinPoint.getSignature().toShortString();
        String clickedMethodWithSessionId = sessionId + methodSignature;

        PreventDoubleClick preventDoubleClick = retrieveDoubleClick(joinPoint);
        if (preventDoubleClickService.isExpired(clickedMethodWithSessionId)) {
            putNewDoubleClickToCache(clickedMethodWithSessionId, preventDoubleClick);

            return joinPoint.proceed();
        }

        return doActionForDoubleClick(joinPoint, preventDoubleClick);
    }

    private Object doActionForDoubleClick(ProceedingJoinPoint joinPoint, PreventDoubleClick preventDoubleClick) throws Throwable {
        if (PreventDoubleClickAction.DOTHROW.equals(preventDoubleClick.action())) {
            throw new PreventDoubleClickMethodCouldNotExecuted(preventDoubleClick.message());
        } else {
            return joinPoint.proceed();
        }
    }

    private void putNewDoubleClickToCache(String doubleClickMethod, PreventDoubleClick preventDoubleClick) {
        Long puttingTime = System.currentTimeMillis();

        Pair<Long, Long> doubleClickMethodPair = Pair.of(puttingTime, preventDoubleClick.inMilliSeconds());
        preventDoubleClickService.put(doubleClickMethod, doubleClickMethodPair);
    }

    private PreventDoubleClick retrieveDoubleClick(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
        Method method = methodSignature.getMethod();

        return method.getDeclaredAnnotation(PreventDoubleClick.class);
    }
}
