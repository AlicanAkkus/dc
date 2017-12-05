package com.aakkus.dc.annotation;


import com.aakkus.dc.enums.ClickableAction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Clickable {

    long inMilliSeconds() default 100;

    String message() default "Requested method could not execute for this time!";

    ClickableAction action() default ClickableAction.DONOTHING;
}