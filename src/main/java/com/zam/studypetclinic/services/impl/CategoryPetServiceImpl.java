package com.zam.studypetclinic.services.impl;

import com.zam.studypetclinic.entity.CategoryPet;
import com.zam.studypetclinic.services.CategoryPetService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryPetServiceImpl implements CategoryPetService {

    @Override
    public Optional<CategoryPet> addCategory(CategoryPet categoryPet) {
        return Optional.empty();
    }
}
