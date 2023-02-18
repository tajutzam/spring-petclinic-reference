package com.zam.studypetclinic.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminAuthDto {

    private String username;
    private String email;
    private String password;
    private String address;

}
