package com.user.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.user.app.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByUserId(Integer userId);
	
	Optional<User> findByUsernameAndPassword(String username, String password);
}
