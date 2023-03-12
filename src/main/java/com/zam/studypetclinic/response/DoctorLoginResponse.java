package com.zam.studypetclinic.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorLoginResponse {

    private String token;


}
