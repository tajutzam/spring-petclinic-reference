package com.zam.studypetclinic.repository;

import com.zam.studypetclinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User , Integer> {


    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
