package com.zam.studypetclinic.controller;

import com.zam.studypetclinic.dto.AdminAuthDto;
import com.zam.studypetclinic.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/admin")
public class AuthAdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody AdminAuthDto adminAuthDto){
        return adminService.add(adminAuthDto);
    }

}
