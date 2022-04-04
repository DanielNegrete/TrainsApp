package com.user.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import com.user.app.client.BookingClient;
import com.user.app.client.TrainClient;
import com.user.app.dto.LoginRequestDTO;
import com.user.app.dto.ResponseDTO;
import com.user.app.dto.TrainRequestDTO;
import com.user.app.dto.UserRequestDTO;
import com.user.app.entity.User;
import com.user.app.exception.LoginFailedException;
import com.user.app.exception.UserNotFoundException;
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
	
	
	//Booking Client
	public List<Booking> getBookings(Integer userId) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		return circuitBreaker.run(() -> bookingClient.getBookingsByUserId(userId), throwable -> getDefaultInfo());
	}
	
	public List<Booking> getDefaultInfo() {
		logger.error("Booking service is down");
		return Collections.emptyList();
	}
	

	//Train Client
	public List<Train> getJourneys(@Valid TrainRequestDTO trainRequestDTO) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		return circuitBreaker.run(() -> trainClient.getTrainsBySourceDestinationDate(trainRequestDTO), throwable -> getDefaultInfoTrain());
	}
	
	public List<Train> getDefaultInfoTrain() {
		logger.error("Train service is down");
		return Collections.emptyList();
	}
	
	
	//User Client
	public List<User> getUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public User saveUsers(UserRequestDTO userRequestDTO) {
		User user = new User();
		BeanUtils.copyProperties(userRequestDTO, user);
		return userRepository.save(user);
	}
	
	public Optional<User> getUserByUserId(Integer userId){
		Optional<User> optionalUser = userRepository.findByUserId(userId);
		if(!optionalUser.isPresent())
			throw new UserNotFoundException("User not found");
		return optionalUser;
	}
	
	public ResponseDTO login(LoginRequestDTO loginRequestDTO) {
		Optional<User> optionalUser = userRepository.findByUsernameAndPassword(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
		ResponseDTO responseDTO = new ResponseDTO("", 0);
		
		if(!optionalUser.isPresent()){
			logger.warn("Wrong username of password");
			throw new LoginFailedException("Bad Credentials");
		}
		else{
			logger.info("User: " + loginRequestDTO.getUsername() + " loged into the system");
			responseDTO.setMessage("User accepted");
			responseDTO.setStatusCode(200);
		}
		return responseDTO;
	}
	

	/*

		
 	public Optional<User> getById(Integer userId){
	return userRepository.findById(userId);
}

	
	public Optional<User> getByUsername(String username)
	{
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if(!optionalUser.isPresent())
			throw new UserNotFoundException("User not found");
		return optionalUser;
	}
	
	public boolean deleteUser(Integer userId) {
		try {
			userRepository.deleteById(userId);
			return true;
		}catch(Exception err) {
			return false;
		}
	}
	 
	 */
}
