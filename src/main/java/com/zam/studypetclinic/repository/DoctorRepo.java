package com.zam.studypetclinic.repository;

import com.zam.studypetclinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
    public Optional<Doctor> findByEmail(String email);
    Optional<List<Doctor>> findByNameContainingIgnoreCase(String name);
    List<Doctor> findByNameLike(String name);
}
