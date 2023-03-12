package com.zam.studypetclinic.dto;

import lombok.Data;

@Data
public class AuthDoctorAuthenticate {
    private String email;
    private String password;
}
