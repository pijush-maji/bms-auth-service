package com.bms.auth.service;

import com.bms.auth.constant.UserRole;
import com.bms.auth.dto.SignUpReq;
import com.bms.auth.dto.SignupRes;
import com.bms.auth.exception.BmsAuthServiceException;
import com.bms.auth.model.BmsUser;
import com.bms.auth.repository.BmsUserRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final BmsUserRepo bmsUserRepo;

    public AuthService(BmsUserRepo bmsUserRepo){
        this.bmsUserRepo=bmsUserRepo;
    }


    public SignupRes signUp(SignUpReq userSignUp) throws BmsAuthServiceException {
        Optional<BmsUser> userExists = bmsUserRepo.findByEmail(userSignUp.getEmail());
        if(userExists.isPresent()){
            throw new BmsAuthServiceException("User with email "+userSignUp.getEmail()+" already exists!");
        }else{
            BmsUser user = new BmsUser();
            user.setName(userSignUp.getName());
            user.setEmail(userSignUp.getEmail());
            user.setPassword(userSignUp.getPassword());
            user.setCreatedAt(String.valueOf(new Date()));
            user.setUpdatedAt(String.valueOf(new Date()));
            user.setRole(UserRole.valueOf(userSignUp.getUserRole()));
            BmsUser save = bmsUserRepo.save(user);
            return new SignupRes("User created successfully");
        }
    }
}
