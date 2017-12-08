package com.aakkus.dc.annotation;


import com.aakkus.dc.enums.PreventDoubleClickAction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventDoubleClick {

    long inMilliSeconds() default 100;

    String message() default "Requested method could not execute for this time!";

    PreventDoubleClickAction action() default PreventDoubleClickAction.DONOTHING;
}