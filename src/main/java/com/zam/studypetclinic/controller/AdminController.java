package com.zam.studypetclinic.controller;

import com.zam.studypetclinic.entity.Admin;
import com.zam.studypetclinic.helper.ResponseHelper;
import com.zam.studypetclinic.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/secured/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<Object> findAll(){
        List<Admin> adminList = adminService.findAll();
        if(adminList.size() == 0){
            return ResponseHelper.generateResponse("data admin is empty" , adminList , HttpStatus.NO_CONTENT);
        }
        return ResponseHelper.generateResponse("data admin found" , adminList , HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(){
        return ResponseHelper.generateResponse("success" , null , HttpStatus.OK);
    }

}
