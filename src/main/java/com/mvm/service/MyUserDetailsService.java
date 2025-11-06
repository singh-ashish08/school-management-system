package com.mvm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mvm.entity.User;
import com.mvm.repository.UserRepo;
import com.mvm.service.impl.UserDetailsImpl;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repo.findByUserName(username);
		if(user==null) {
			System.out.println("user 404");
			throw new UsernameNotFoundException("User 404");
		}
		return new UserDetailsImpl(user);//returning object of UserDetails
	}

}
