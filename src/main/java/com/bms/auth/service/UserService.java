package com.bms.auth.service;

import com.bms.auth.constant.AppConstant;
import com.bms.auth.exception.BmsUserServiceException;
import com.bms.auth.model.BmsUser;
import com.bms.auth.repository.BmsUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final String CLASS_NAME="UserService";
    private final BmsUserRepo bmsUserRepo;

    public UserService(BmsUserRepo bmsUserRepo){
        this.bmsUserRepo=bmsUserRepo;
    }

    public BmsUser getUserByEmail(String email){
        String METHOD_NAME="getUserByEmail";
        log.info("{}.{} -> {}",CLASS_NAME,METHOD_NAME, AppConstant.METHOD_START);
        if(email==null || email.isEmpty()){
            log.info("Email is null or empty");
            throw new BmsUserServiceException("Invalid email id");
        }
        log.info("{}.{} -> {}",CLASS_NAME,METHOD_NAME, AppConstant.METHOD_END);
        return bmsUserRepo.findByEmail(email).orElseThrow(
                ()-> new BmsUserServiceException("No user is present with email"+email));
    }
}
