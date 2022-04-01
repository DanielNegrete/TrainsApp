package com.user.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import com.user.app.client.BookingClient;
import com.user.app.client.TrainClient;
import com.user.app.controller.UserController;
import com.user.app.dto.LoginRequestDTO;
import com.user.app.dto.ResponseDTO;
import com.user.app.dto.TrainRequestDTO;
import com.user.app.entity.User;
import com.user.app.model.Booking;
import com.user.app.model.Train;
import com.user.app.repository.UserRepository;


@Service
public class UserService {
	
	private static final Logger logger = Logger.getLogger(UserService.class);
	
	{DOMConfigurator.configure("src/main/resources/log4j.xml");}
	
	@Autowired
	CircuitBreakerFactory circuitBreakerFactory;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookingClient bookingClient;
	
	@Autowired
	TrainClient trainClient;
	
	public List<Booking> getBookings(Integer userId) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		return circuitBreaker.run(() -> bookingClient.getBookingsByUserId(userId), throwable -> getDefaultInfo());
	}
	
	public List<Booking> getDefaultInfo() {
		logger.error("Booking service is down");
		return Collections.emptyList();
	}
	
	public List<User> getUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public User saveUsers(User user) {
		return userRepository.save(user);
	}
	
	public Optional<User> getById(Integer userId){
		return userRepository.findById(userId);
	}
	
	
	public Optional<User> getByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}
	
	public boolean deleteUser(Integer userId) {
		try {
			userRepository.deleteById(userId);
			return true;
		}catch(Exception err) {
			return false;
		}
	}
	
	public Optional<User> getUserByUserId(Integer userId){
		return userRepository.findByUserId(userId);
	}
	
	public ResponseDTO login(@Valid LoginRequestDTO loginRequestDTO) {
		Optional<User> optionalUser = userRepository.findByUsernameAndPassword(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
		ResponseDTO responseDTO = new ResponseDTO("", 0);
		
		if(!optionalUser.isPresent()){
			logger.warn("Wrong username of password");
			responseDTO.setMessage("Bad credentials");
			responseDTO.setStatusCode(404);
		}
		else{
			logger.info("User: " + loginRequestDTO.getUsername() + " loged into the system");
			responseDTO.setMessage("User accepted");
			responseDTO.setStatusCode(200);
		}
		return responseDTO;
	}
	
	
	public List<Booking> getBookingsByUserId(Integer userId){
		return bookingClient.getBookingsByUserId(userId);
	}
	
	
	public List<Train> getTrainsBySourceDestinationDate(TrainRequestDTO trainRequestDTO){
		return trainClient.getTrainsBySourceDestinationDate(trainRequestDTO);
	}
}
