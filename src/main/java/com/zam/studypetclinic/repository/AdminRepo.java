package com.zam.studypetclinic.repository;

import com.zam.studypetclinic.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin , Integer> {
    public Optional<Admin> findByUsername(String username);
}
