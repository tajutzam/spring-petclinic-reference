package com.zam.studypetclinic.services.impl;

import com.zam.studypetclinic.dto.AuthDoctorAuthenticate;
import com.zam.studypetclinic.dto.DoctorUpdateDto;
import com.zam.studypetclinic.entity.Doctor;
import com.zam.studypetclinic.exceptions.ApiRequestException;
import com.zam.studypetclinic.repository.DoctorRepo;
import com.zam.studypetclinic.response.DoctorLoginResponse;
import com.zam.studypetclinic.services.DoctorService;
import com.zam.studypetclinic.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DoctorServiceImpl implements UserDetailsService  ,  DoctorService {
    @Autowired
    private  DoctorRepo doctorRepo;
    @Autowired
    private  JwtService jwtService;

    @Autowired
    @Lazy
    private  PasswordEncoder passwordEncoder;

    @Override
    public List<Doctor> findAll() {
        return doctorRepo.findAll();
    }
    @Override
    public Optional<Doctor> addEmployee(Doctor employee) {
        Optional<Doctor> optionalEmployee = doctorRepo.findByEmail(employee.getEmail());
        if(optionalEmployee.isPresent()){
            return Optional.empty();
        }else{
            Doctor saved = doctorRepo.save(employee);
            return Optional.of(saved);
        }
    }

    @Override
    public Doctor loadUserByUsername(String email)  {
        return doctorRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Doctor with username "+ email + " not found"));
    }
    @Override
    public Optional<DoctorLoginResponse> login(AuthDoctorAuthenticate authEmployeeAuthenticate) {
        UserDetails details = this.loadUserByUsername(authEmployeeAuthenticate.getEmail());
        if(details != null){
            boolean matches = passwordEncoder.matches(authEmployeeAuthenticate.getPassword(), details.getPassword());
            if(matches){
                String token = jwtService.generateToken(details);
                var response = DoctorLoginResponse.builder()
                        .token(token)
                        .build();
                return Optional.ofNullable(response);
            }{
                throw new ApiRequestException("Password salah");
            }
        }
        return Optional.empty();
    }
    @Override
    public Doctor update(DoctorUpdateDto doctor) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(doctor.getId());
        if(optionalDoctor.isPresent()){
            var dataDoctor = Doctor.builder()
                    .id(doctor.getId())
                    .name(doctor.getName())
                    .address(doctor.getAddress())
                    .password(passwordEncoder.encode(doctor.getPassword()))
                    .email(doctor.getEmail())
                    .build();
            return doctorRepo.save(dataDoctor);
        }else{
            throw new ApiRequestException("doctor not found");
        }
    }
    @Override
    public Optional<Doctor> findById(Integer id) {
        return doctorRepo.findById(id);
    }
    @Override
    public Optional<List<Doctor>> findByName(String name) {
         List<Doctor> doctors =  doctorRepo.findByNameLike("%"+name+"%");
         if(doctors.size() > 0){
             return Optional.of(doctors);
         }
         return
                 Optional.empty();
    }
    @Override
    public boolean deleteById(Integer id) {
        return findById(id).map(doctor -> {
            doctorRepo.deleteById(id);
            return true;
        }).orElse(false);
    }
}
