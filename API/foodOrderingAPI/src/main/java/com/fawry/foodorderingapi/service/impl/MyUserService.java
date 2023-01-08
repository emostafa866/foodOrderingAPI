package com.fawry.foodorderingapi.service.impl;

import com.fawry.foodorderingapi.entity.MyUser;
import com.fawry.foodorderingapi.exception.RecordNotFoundException;
import com.fawry.foodorderingapi.model.UsersDto;
import com.fawry.foodorderingapi.model.MyUserRole;
import com.fawry.foodorderingapi.repository.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    private MyUserRepo myUserRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return myUserRepo.findByEmail(email)
                .orElseThrow(()->new RecordNotFoundException("user Not Found"));
    }


    public void SignUp(UsersDto userDto) {

        boolean isExist = myUserRepo.findByEmail(userDto.getEmail()).isPresent();

        if(isExist){
            throw new IllegalStateException("email already exists");
        }

        String encodePass=bCryptPasswordEncoder.encode(userDto.getPassword());
        MyUser user= new MyUser();

        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setPassword(encodePass);
        user.setMyUserRole(MyUserRole.USER);

        myUserRepo.save(user);

    }

    public MyUser getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = auth.getName();
        MyUser currentUser = myUserRepo.findByEmail(currentUserName).orElseThrow(
                ()-> new RecordNotFoundException("User NOt Found")
        );

        return currentUser;
    }

}
