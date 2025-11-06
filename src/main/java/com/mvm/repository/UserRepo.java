package com.mvm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvm.entity.User;

public interface UserRepo extends JpaRepository<User,String> {

	User findByUserName(String username);

}
