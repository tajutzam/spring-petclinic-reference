package com.zam.studypetclinic.controller.doctor;


import com.zam.studypetclinic.dto.AuthDoctorDtoSignUp;
import com.zam.studypetclinic.dto.DoctorUpdateDto;
import com.zam.studypetclinic.entity.Doctor;
import com.zam.studypetclinic.helper.ResponseHelper;
import com.zam.studypetclinic.services.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/doctor")
@AllArgsConstructor
public class DoctorController {

    @Autowired
    private final DoctorService doctorService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody AuthDoctorDtoSignUp authEmployeeDtoSignUp){
        var employee = com.zam.studypetclinic.entity.Doctor.builder()
                .email(authEmployeeDtoSignUp.getEmail())
                .address(authEmployeeDtoSignUp.getAddress())
                .password(passwordEncoder.encode(authEmployeeDtoSignUp.getPassword()))
                .isEnable(false)
                .name(authEmployeeDtoSignUp.getName())
                .build();
        Optional<com.zam.studypetclinic.entity.Doctor> addEmployee = doctorService.addEmployee(employee);
        if(addEmployee.isPresent()){
            return ResponseHelper.generateResponse("Success add employee" , addEmployee.get(), HttpStatus.CREATED);
        }
        return ResponseHelper.generateResponse("Failed to add employee email has be taken" , null, HttpStatus.BAD_REQUEST);
    };

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody DoctorUpdateDto updateDto){
        Doctor update = doctorService.update(updateDto);
        return ResponseHelper.generateResponse("success update doctor" , update , HttpStatus.OK);
    }




}
