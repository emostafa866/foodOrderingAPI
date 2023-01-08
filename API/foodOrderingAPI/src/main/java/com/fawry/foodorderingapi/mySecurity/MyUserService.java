package com.fawry.foodorderingapi.mySecurity;

import com.fawry.foodorderingapi.exception.RecordNotFoundException;
import com.fawry.foodorderingapi.mapper.NewUserDTOAndAppUserEntityMapper;
import com.fawry.foodorderingapi.model.UsersDto;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
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

}
