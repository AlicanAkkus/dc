package com.aakkus.dc.controller;

import com.aakkus.dc.annotation.Clickable;
import com.aakkus.dc.enums.ClickableAction;
import com.aakkus.dc.request.SampleRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Clickable
    @GetMapping("/test")
    public String getrq() {
        return "Test executed.";
    }

    @Clickable
    @PostMapping("/test")
    public String postrq(@RequestBody SampleRequest request) {
        return "Test executed.";
    }

    @Clickable(inMilliSeconds = 5000L, action = ClickableAction.DOTHROW)
    @PostMapping("/test2")
    public String postrq2(@RequestBody SampleRequest request, @RequestParam(value = "name", required = false) String name) {
        return "Test executed.";
    }
}
