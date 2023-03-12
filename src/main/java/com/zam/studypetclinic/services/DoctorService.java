package com.zam.studypetclinic.services;

import com.zam.studypetclinic.dto.AuthDoctorAuthenticate;
import com.zam.studypetclinic.dto.DoctorUpdateDto;
import com.zam.studypetclinic.entity.Doctor;
import com.zam.studypetclinic.response.DoctorLoginResponse;

import java.util.List;
import java.util.Optional;


public interface DoctorService {

     List<Doctor> findAll();
     Optional<Doctor> addEmployee(Doctor doctor);

     Optional<DoctorLoginResponse> login(AuthDoctorAuthenticate authEmployeeAuthenticate);

     Doctor update(DoctorUpdateDto doctor);

     boolean deleteById(Integer id);

     Optional<Doctor> findById(Integer id);

     Optional<List<Doctor>> findByName(String name);

}
