package com.zam.studypetclinic.controller;

import com.zam.studypetclinic.exceptions.ApiRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/unsecure")
public class AnonymousController {

    @GetMapping("/pet")
    public ResponseEntity<Object> findAllPet(){
        return null;
    }

    @GetMapping("/hai")
    public String hallo(){
        throw new ApiRequestException("request throw");
    }

}
