package com.bms.auth.controller;

import com.bms.auth.constant.AppConstant;
import com.bms.auth.model.BmsUser;
import com.bms.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final String CLASS_NAME="UserController";

    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<BmsUser> getUser(@PathVariable String email){
        var METHOD_NAME = "getUser";
        log.info("{}.{} {}",CLASS_NAME,METHOD_NAME, AppConstant.METHOD_START);
        BmsUser userByEmail = userService.getUserByEmail(email);
        log.info("{}.{} {}",CLASS_NAME,METHOD_NAME, AppConstant.METHOD_END);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }
}
