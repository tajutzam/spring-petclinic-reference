package com.zam.studypetclinic.repository;

import com.zam.studypetclinic.entity.CategoryPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryPetRepo extends JpaRepository<CategoryPet , Integer> {

    public Optional<CategoryPet> findByName(String categoryName);

}
