package com.bms.auth.service;

import com.bms.auth.constant.UserRole;
import com.bms.auth.dto.LoginReq;
import com.bms.auth.dto.SignUpReq;
import com.bms.auth.dto.SignupRes;
import com.bms.auth.exception.BmsAuthServiceException;
import com.bms.auth.model.BmsUser;
import com.bms.auth.repository.BmsUserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final BmsUserRepo bmsUserRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(BmsUserRepo bmsUserRepo,
                       PasswordEncoder passwordEncoder){
        this.bmsUserRepo=bmsUserRepo;
        this.passwordEncoder=passwordEncoder;
    }


    public SignupRes signUp(SignUpReq userSignUp) throws BmsAuthServiceException {
        Optional<BmsUser> userExists = bmsUserRepo.findByEmail(userSignUp.getEmail());
        if(userExists.isPresent()){
            throw new BmsAuthServiceException("User with email "+userSignUp.getEmail()+" already exists!");
        }else{
            BmsUser user = new BmsUser();
            user.setName(userSignUp.getName());
            user.setEmail(userSignUp.getEmail());
            user.setPassword(passwordEncoder.encode(userSignUp.getPassword()));
            user.setCreatedAt(String.valueOf(new Date()));
            user.setUpdatedAt(String.valueOf(new Date()));
            user.setRole(UserRole.valueOf(userSignUp.getUserRole()));
            BmsUser save = bmsUserRepo.save(user);
            return new SignupRes("User created successfully");
        }
    }

    public BmsUser authenticate(LoginReq loginReq) throws BmsAuthServiceException {
        return bmsUserRepo.findByEmail(loginReq.getEmail()).orElseThrow(
                ()-> new BmsAuthServiceException("No User with the following email"+loginReq.getEmail()));
    }
}
