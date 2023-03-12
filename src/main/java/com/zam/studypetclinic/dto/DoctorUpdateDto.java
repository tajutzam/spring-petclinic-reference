package com.zam.studypetclinic.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorUpdateDto {
    private Integer id;
    private String name;
    private String email;
    private String address;
    private String password;

}
