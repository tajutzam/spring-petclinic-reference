package com.zam.studypetclinic.controller.user;

import com.zam.studypetclinic.dto.UserRegisterRequest;
import com.zam.studypetclinic.entity.User;
import com.zam.studypetclinic.helper.ResponseHelper;
import com.zam.studypetclinic.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/user/")
public class AuthUserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody UserRegisterRequest request){
        User user = userService.addUser(request);
        return ResponseHelper.generateResponse("success register" , user , HttpStatus.OK);
    }

}
