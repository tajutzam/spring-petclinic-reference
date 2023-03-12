package com.zam.studypetclinic.controller.doctor;

import com.zam.studypetclinic.dto.AuthDoctorAuthenticate;
import com.zam.studypetclinic.helper.ResponseHelper;
import com.zam.studypetclinic.response.DoctorLoginResponse;
import com.zam.studypetclinic.services.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/doctor/auth")
public class AuthDoctorController {

    private final DoctorService employeeService;
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthDoctorAuthenticate authenticate){
        Optional<DoctorLoginResponse> login = employeeService.login(authenticate);
        if(login.isPresent()){
            return ResponseHelper.generateResponse("success login" , login.get() , HttpStatus.OK );
        }
        return ResponseHelper.generateResponse("failed to login" , null , HttpStatus.BAD_REQUEST);
    }

}
