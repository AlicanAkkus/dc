package com.aakkus.dc.controller;

import com.aakkus.dc.service.PreventDoubleClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreventDoubleClickCacheController {

    @Autowired
    PreventDoubleClickService preventDoubleClickService;

    @GetMapping("/preventDoubleClick/flushAll")
    public String flushAll() {
        preventDoubleClickService.flushAll();
        return "success";
    }
}
