package com.bms.auth.controller;

import com.bms.auth.dto.LoginReq;
import com.bms.auth.dto.LoginRes;
import com.bms.auth.dto.SignUpReq;
import com.bms.auth.dto.SignupRes;
import com.bms.auth.exception.BmsAuthServiceException;
import com.bms.auth.model.BmsUser;
import com.bms.auth.service.AuthService;
import com.bms.auth.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil){
        this.authService=authService;
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) throws BmsAuthServiceException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(),loginReq.getPassword()));
            BmsUser user = authService.authenticate(loginReq);
            String token = jwtUtil.generateToken(user, user.getRole());
            LoginRes loginRes = new LoginRes();
            loginRes.setToken(token);
            return new ResponseEntity<>(loginRes,HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BmsAuthServiceException("Invalid Credentials!");
        }
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
