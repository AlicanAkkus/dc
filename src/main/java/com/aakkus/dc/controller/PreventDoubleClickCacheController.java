package com.aakkus.dc.controller;

import com.aakkus.dc.service.PreventDoubleClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dc/caches")
public class PreventDoubleClickCacheController {

    @Autowired
    PreventDoubleClickService preventDoubleClickService;

    @GetMapping
    public String flushAll() {
        preventDoubleClickService.flushAll();
        return "success";
    }

    @GetMapping("/{key}")
    public String flushByKey(String key) {
        preventDoubleClickService.flushByKey(key);
        return "success";
    }
}
