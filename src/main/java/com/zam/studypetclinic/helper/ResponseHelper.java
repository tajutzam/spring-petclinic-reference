package com.zam.studypetclinic.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHelper {

    public static ResponseEntity<Object> generateResponse(String message , Object obj , HttpStatus status){
       Map<String , Object> data = new HashMap<>();
       data.put("message" , message);
       data.put("code" , status);
       data.put("payload" , obj);
       return ResponseEntity.status(status).body(data);
    }

}
