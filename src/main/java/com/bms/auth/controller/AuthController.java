package com.bms.auth.controller;

import com.bms.auth.dto.SignUpReq;
import com.bms.auth.dto.SignupRes;
import com.bms.auth.exception.BmsAuthServiceException;
import com.bms.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/login")
    public String login(){
        return "Logged in";
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupRes> singUp(@Valid @RequestBody SignUpReq userSignUp, BindingResult bindingResult)
            throws BmsAuthServiceException {
        if(bindingResult.hasErrors()){
            log.info("Invalid request data->"+ Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return new ResponseEntity<>(new SignupRes("Invalid request data"), HttpStatus.BAD_REQUEST);
        }
        SignupRes signupRes = authService.signUp(userSignUp);
        return new ResponseEntity<>(signupRes, HttpStatusCode.valueOf(201));
    }
}
