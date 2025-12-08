package com.mvm.controller;

import com.mvm.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvm.entity.User;
import com.mvm.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService service;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {

        return service.register(user);
	}

    @PostMapping("/login")
    public String login(@RequestBody User user){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUserName());//create generateToken in JwtService class to generate token for the user passed into it
        }else{
                return "login failed";
        }
    }
//check the jwt token got as output on jwt.io website if it is valid token or not
}
//UsernamePasswordAuthenticationToken is inbuild in spring security
// which will verify a username and password everytime it will generate a token,with that we can check if the user is valid or not