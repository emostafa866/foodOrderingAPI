package com.fawry.foodorderingapi.resources;


import com.fawry.foodorderingapi.model.UsersDto;
import com.fawry.foodorderingapi.service.impl.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MyUserController {


    @Autowired
    private MyUserService myUserService;

    @PostMapping("registration")
    public String register(@RequestBody UsersDto userDto){


        myUserService.SignUp(userDto);

        return "user created";
    }
}
