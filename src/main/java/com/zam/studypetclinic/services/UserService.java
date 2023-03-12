package com.zam.studypetclinic.services;

import com.zam.studypetclinic.dto.UserRegisterRequest;
import com.zam.studypetclinic.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User addUser(UserRegisterRequest user);

}
