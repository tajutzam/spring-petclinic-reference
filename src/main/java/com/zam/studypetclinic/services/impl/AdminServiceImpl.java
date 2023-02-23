package com.zam.studypetclinic.services.impl;

import com.zam.studypetclinic.dto.AdminAuthDto;
import com.zam.studypetclinic.dto.AdminAuthLoginDto;
import com.zam.studypetclinic.entity.Admin;
import com.zam.studypetclinic.exceptions.ApiRequestException;
import com.zam.studypetclinic.helper.ResponseHelper;
import com.zam.studypetclinic.repository.AdminRepo;
import com.zam.studypetclinic.response.AdminLoginResponse;
import com.zam.studypetclinic.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Optional<Admin> update(Admin admin) {
        Optional<Admin> adminOptional = findById(admin.getId());
        if(adminOptional.isPresent()){
            return Optional.of(adminRepo.save(adminOptional.get()));
        }else{
            throw new ApiRequestException("failed to update admin , id not found");
        }
    }

    @Override
    public Optional<Admin> addFromAdminDashboard(AdminAuthDto adminAuthDto) {
        Optional<Admin> adminRepoByUsername = adminRepo.findByUsername(adminAuthDto.getUsername());
        Optional<Admin> adminRepoByEmail = adminRepo.findByEmail(adminAuthDto.getEmail());
        if(adminRepoByUsername.isPresent()){
            throw new ApiRequestException("Username harus sudah digunakan coba username yang lain");
        }
        if(adminRepoByEmail.isPresent()){
            throw new ApiRequestException("Email harus sudah digunakan coba username yang lain");
        }
        var admin = Admin.builder()
                .username(adminAuthDto.getUsername())
                .email(adminAuthDto.getEmail())
                .address(adminAuthDto.getAddress())
                .password(this.passwordEncoder.encode(adminAuthDto.getPassword()))
                .build();
        Admin savedAdmin = adminRepo.save(admin);
        return Optional.ofNullable(savedAdmin);
    }

    @Override
    public Admin loadUserByUsername(String username)  {
        return adminRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("admin with username "+ username + " not found"));
    }

    @Override
    public boolean deleteById(Integer id) {
        Optional<Admin> adminRepoById = adminRepo.findById(id);
        if(adminRepoById.isPresent()){
            try{
                adminRepo.deleteById(id);
                return true;
            }catch (Exception e){
                throw new ApiRequestException(e.getMessage());
            }
        }
        throw new ApiRequestException("gagal menghapus data admin , data tidak ditemukan");
    }
    @Override
    public AdminLoginResponse login(AdminAuthLoginDto adminAuthLoginDto) {
        Optional<Admin> optionalAdmin = adminRepo.findByUsername(adminAuthLoginDto.getUsername());
        if(optionalAdmin.isPresent()){
            if(this.passwordEncoder.matches(adminAuthLoginDto.getPassword(), optionalAdmin.get().getPassword())){
                String token = this.jwtService.generateToken(optionalAdmin.get());
                return AdminLoginResponse.
                        builder().
                        token(token).
                        date(new Date(System.currentTimeMillis())).
                        build();
            }else{
                throw new ApiRequestException("password salah");
            }
        }else{
            throw new ApiRequestException("Username salah");
        }
    }
}
