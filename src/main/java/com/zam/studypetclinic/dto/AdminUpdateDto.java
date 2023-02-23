package com.zam.studypetclinic.dto;
import lombok.Data;

@Data
public class AdminUpdateDto  {
    private Integer id;
    private String name;
    private String address;
    private String email;
    private String username;
    private String password;
}
