package com.zam.studypetclinic.dto;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String username;
    private String email;
    private String password;
    private String address;
    private String name;

}
