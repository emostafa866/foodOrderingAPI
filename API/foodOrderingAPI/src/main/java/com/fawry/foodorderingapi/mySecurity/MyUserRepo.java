package com.fawry.foodorderingapi.mySecurity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepo extends JpaRepository<MyUser,Long> {

   Optional<MyUser>findByEmail(String email);
}
