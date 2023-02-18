package com.zam.studypetclinic.services.impl;

import com.zam.studypetclinic.dto.AdminAuthDto;
import com.zam.studypetclinic.entity.Admin;
import com.zam.studypetclinic.helper.ResponseHelper;
import com.zam.studypetclinic.repository.AdminRepo;
import com.zam.studypetclinic.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService , UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private JwtServiceImpl jwtService;
    @Override
    public List<Admin> findAll() {
        return this.adminRepo.findAll();
    }

    @Override
    public ResponseEntity<Object> add(AdminAuthDto adminAuthDto) {
        try{
            var admin = Admin.builder()
                    .username(adminAuthDto.getUsername())
                    .email(adminAuthDto.getEmail())
                    .password(passwordEncoder.encode(adminAuthDto.getPassword()))
                    .address(adminAuthDto.getAddress())
                    .build();
            Admin saved = this.adminRepo.save(admin);
            String token = this.jwtService.generateToken(saved);

            return ResponseHelper.generateResponse("Success registrasi", token, HttpStatus.OK);
        }catch (RuntimeException e){
            throw new RuntimeException("terjadi kesalahan");
        }
    }

    @Override
    public Optional<Admin> findById(Integer id) {
        return adminRepo.findById(id);
    }

    @Override
    public boolean update(Admin admin) {
        Optional<Admin> adminOptional = findById(admin.getId());
        if(adminOptional.isEmpty()){
            return false;
        }else{
            adminRepo.save(admin);
            return true;
        }
    }

    @Override
    public Admin loadUserByUsername(String username)  {
        return adminRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("admin with username "+ username + " not found"));
    }
}
