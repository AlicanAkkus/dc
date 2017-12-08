package com.aakkus.dc.controller;

import com.aakkus.dc.annotation.PreventDoubleClick;
import com.aakkus.dc.enums.PreventDoubleClickAction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PreventDoubleClick(inMilliSeconds = 100000L, action = PreventDoubleClickAction.DOTHROW)
    @GetMapping("/test")
    public String getrq() {
        return "Test executed.";
    }
}