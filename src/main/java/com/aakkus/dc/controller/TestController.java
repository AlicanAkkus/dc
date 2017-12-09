package com.aakkus.dc.controller;

import com.aakkus.dc.annotation.PreventDoubleClick;
import com.aakkus.dc.enums.PreventDoubleClickAction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    @PreventDoubleClick(inMilliSeconds = 100000L, action = PreventDoubleClickAction.DOTHROW)
    public String getrq() {
        return "Test executed.";
    }

    @GetMapping("/test2")
    @ResponseStatus(HttpStatus.OK)
    @PreventDoubleClick(inMilliSeconds = 100000L, action = PreventDoubleClickAction.DONOTHING)
    public String getrq2() {
        return "Test executed.";
    }
}