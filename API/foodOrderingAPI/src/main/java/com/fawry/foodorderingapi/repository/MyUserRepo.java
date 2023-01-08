package com.fawry.foodorderingapi.repository;

import com.fawry.foodorderingapi.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepo extends JpaRepository<MyUser,Long> {

   Optional<MyUser>findByEmail(String email);
}
