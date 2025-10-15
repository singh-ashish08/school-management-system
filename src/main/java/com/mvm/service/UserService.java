package com.mvm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mvm.entity.User;
import com.mvm.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public User register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		System.out.println(user.getPassword());
		return repo.save(user);
	}

}
