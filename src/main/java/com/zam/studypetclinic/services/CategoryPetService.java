package com.zam.studypetclinic.services;


import com.zam.studypetclinic.entity.CategoryPet;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CategoryPetService {

    public Optional<CategoryPet> addCategory(CategoryPet categoryPet);
    

}
