package com.zam.studypetclinic.repository;

import com.zam.studypetclinic.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepo extends JpaRepository<Pet , Integer> {
}
