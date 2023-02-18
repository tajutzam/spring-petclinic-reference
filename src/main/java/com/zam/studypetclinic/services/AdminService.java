package com.zam.studypetclinic.services;

import com.zam.studypetclinic.dto.AdminAuthDto;
import com.zam.studypetclinic.entity.Admin;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    public List<Admin> findAll();
    public ResponseEntity<Object> add(AdminAuthDto admin);
    public Optional<Admin> findById(Integer id);
    public boolean update(Admin admin);

}
