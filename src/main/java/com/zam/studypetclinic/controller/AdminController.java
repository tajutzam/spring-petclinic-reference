package com.zam.studypetclinic.controller;

import com.zam.studypetclinic.dto.AdminAuthDto;
import com.zam.studypetclinic.dto.AdminUpdateDto;
import com.zam.studypetclinic.dto.KeyDto;
import com.zam.studypetclinic.entity.Admin;
import com.zam.studypetclinic.helper.ResponseHelper;
import com.zam.studypetclinic.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/secured/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<Object> findAll() {
        List<Admin> adminList = adminService.findAll();
        if (adminList.size() == 0) {
            return ResponseHelper.generateResponse("data admin is empty", adminList, HttpStatus.NO_CONTENT);
        }
        return ResponseHelper.generateResponse("data admin found", adminList, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody AdminAuthDto adminAuthDto) {
        return adminService.addFromAdminDashboard(adminAuthDto)
                .map(admin -> ResponseHelper.generateResponse("berhasil menambahkan data admin baru", admin, HttpStatus.OK))
                .orElse(ResponseHelper.generateResponse("gagal menambahkan data admin baru , terjadi kesalahan", null, HttpStatus.BAD_REQUEST));
    }
    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody AdminUpdateDto adminUpdateDto) {
        var admin = Admin.builder()
                .id(adminUpdateDto.getId())
                .username(adminUpdateDto.getUsername())
                .password(adminUpdateDto.getPassword())
                .address(adminUpdateDto.getAddress())
                .name(adminUpdateDto.getName())
                .email(adminUpdateDto.getEmail())
                .build();
        Optional<Admin> adminOptional = adminService.update(admin);
        if (adminOptional.isPresent()) {
            return ResponseHelper.generateResponse(
                    "Success update admin",
                    admin,
                    HttpStatus.OK
            );
        }
        return ResponseHelper.generateResponse(
                "Failed to update admin",
                null,
                HttpStatus.BAD_REQUEST
        );
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteById(@RequestBody KeyDto<Integer> keyDto){
        boolean isDelete = adminService.deleteById(keyDto.getKey());
        return ResponseHelper.generateResponse("berhasil menghapus data admin" , true , HttpStatus.OK);
    }

}
