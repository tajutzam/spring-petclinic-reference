package com.zam.studypetclinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secure/tes")
public class SecuredTestController {

    @GetMapping
    public String secure(){
        return "hllo";
    }
}
