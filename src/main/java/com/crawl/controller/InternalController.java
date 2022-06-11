package com.crawl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("internal/api")
public class InternalController {

    @GetMapping("network")
    public String network(){
        return "Connect Successful !";
    }
}
