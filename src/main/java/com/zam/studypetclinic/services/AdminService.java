package com.zam.studypetclinic.services;

import com.zam.studypetclinic.dto.AdminAuthDto;
import com.zam.studypetclinic.dto.AdminAuthLoginDto;
import com.zam.studypetclinic.entity.Admin;
import com.zam.studypetclinic.response.AdminLoginResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    public List<Admin> findAll();
    public ResponseEntity<Object> add(AdminAuthDto admin);
    public Optional<Admin> findById(Integer id);
    public Optional<Admin> update(Admin admin);

    public Optional<Admin> addFromAdminDashboard(AdminAuthDto adminAuthDto);

    public boolean deleteById(Integer id);

    public AdminLoginResponse login(AdminAuthLoginDto adminAuthLoginDto);
}
