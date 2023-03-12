package com.zam.studypetclinic.controller.admin;

import com.zam.studypetclinic.dto.AdminAuthDto;
import com.zam.studypetclinic.dto.AdminUpdateDto;
import com.zam.studypetclinic.dto.KeyDto;
import com.zam.studypetclinic.entity.Admin;
import com.zam.studypetclinic.entity.Doctor;
import com.zam.studypetclinic.helper.ResponseHelper;
import com.zam.studypetclinic.services.AdminService;
import com.zam.studypetclinic.services.DoctorService;
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

    @Autowired
    private DoctorService doctorService;

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

    @GetMapping("/doctor")
    public ResponseEntity<List<com.zam.studypetclinic.entity.Doctor>> findAllDoctor(){
        return ResponseEntity.of(Optional.ofNullable(doctorService.findAll()));
    }


    @DeleteMapping("/doctor/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") Integer id){
        boolean isDelete = doctorService.deleteById(id);
        if (isDelete){
            return ResponseHelper.generateResponse("Success delete doctor" , true , HttpStatus.OK );
        }else{
            return ResponseHelper.generateResponse("Failed  to delete doctor , id doctor not found" , false , HttpStatus.NOT_FOUND );
        }
    }
    @GetMapping("/doctor/search/{name}")
    public ResponseEntity<Object> findByName(@PathVariable("name") String name){
        System.out.println(name);
        Optional<List<Doctor>> optionalDoctorList = doctorService.findByName(name);
        return optionalDoctorList
                .map(doctors -> ResponseHelper.generateResponse("Doctor match", doctors, HttpStatus.OK))
                .orElseGet(() -> ResponseHelper.generateResponse("Doctor not match", null, HttpStatus.NOT_FOUND));
    }

}
