package com.booking.app.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booking.app.model.User;

@FeignClient(name = "user", url = "http://localhost:9093")
public interface UserClient {
	
	@GetMapping("/user")
	List<User> getUser();
	
	@GetMapping("/user/{userId}")
	Optional<User> getUserByUserId(@PathVariable("userId") Integer userId);
}
