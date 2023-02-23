package com.zam.studypetclinic.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AdminLoginResponse {

    private String token;
    private Date date;

}
