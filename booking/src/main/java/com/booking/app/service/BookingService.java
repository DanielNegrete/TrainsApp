package com.booking.app.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import com.booking.app.client.TrainClient;
import com.booking.app.client.UserClient;
import com.booking.app.controller.BookingController;
import com.booking.app.dto.BookingRequestDTO;
import com.booking.app.entity.Booking;
import com.booking.app.exception.TrainNotFoundException;
import com.booking.app.exception.UserNotFoundException;
import com.booking.app.model.Train;
import com.booking.app.model.User;
import com.booking.app.repository.BookingRepo;

@Service
public class BookingService {
	
	private static final Logger logger = Logger.getLogger(BookingController.class);
	
	{DOMConfigurator.configure("src/main/resources/log4j.xml");}
	
	@Autowired
	CircuitBreakerFactory circuitBreakerFactory;
	
	@Autowired
	BookingRepo bookingRepo;
	
	@Autowired
	UserClient userClient;
	
	@Autowired
	TrainClient trainClient;
	
	
	//Booking service
	public List<Booking> getBookings(){
		return bookingRepo.findAll();
	}
	
	public List<Booking> getBookingByUserId(Integer userId) {
		Optional<User> optionalUser = userClient.getUserByUserId(userId);
		if(!optionalUser.isPresent()) {
			logger.error("User: " + userId + " not found");
			throw new UserNotFoundException("User not found: " + userId);
		}
		return bookingRepo.findByUserId(userId);
	}
	
	public Booking saveBooking(BookingRequestDTO bookingRequestDTO){
		Optional<User> optionalUser = getUser(bookingRequestDTO.getUserId());
		Optional<Train> optionalTrain = getTrain(bookingRequestDTO.getTrainId());
		
		if(!optionalUser.isPresent()) {
			logger.error("User: " + bookingRequestDTO.getUserId() + " not found");
			throw new UserNotFoundException("User not found: " + bookingRequestDTO.getUserId());
		}
		
		if(!optionalTrain.isPresent()) {
			logger.error("Train: " + bookingRequestDTO.getTrainId() + " not found");
			throw new TrainNotFoundException("Train not found: " + bookingRequestDTO.getTrainId());
		}
		
		Booking booking = new Booking();
		BeanUtils.copyProperties(bookingRequestDTO, booking);
		booking.setTotalPrice(bookingRequestDTO.getTicketsQuantity() * optionalTrain.get().getPrice());
		return bookingRepo.save(booking);
	}
	
		
	//User service
	public Optional<User> getUser(Integer userId) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		return circuitBreaker.run(() -> userClient.getUserByUserId(userId), throwable -> getDefaultInfoUser());
	}
	
	public Optional<User> getDefaultInfoUser() {
		logger.warn("User service is down");
		return Optional.empty();
	}
	
	public List<User> getUsersService() {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		return circuitBreaker.run(() -> userClient.getUser(), throwable -> getDefaultInfoUsers());
	}
	
	public List<User> getDefaultInfoUsers() {
		logger.warn("User service is down");
		return Collections.emptyList();
	}
	
	
	//Train service
	public Optional<Train> getTrain(Integer trainId) {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		return circuitBreaker.run(() -> trainClient.getTrainByTrainId(trainId), throwable -> getDefaultInfoTrain());
	}
	
	public Optional<Train> getDefaultInfoTrain() {
		logger.warn("Train service is down");
		return Optional.empty();
	}
	
	public List<Train> getTrainsService() {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
		return circuitBreaker.run(() -> trainClient.getTrains(), throwable -> getDefaultInfoTrains());
	}
	
	public List<Train> getDefaultInfoTrains() {
		logger.warn("Train service is down");
		return Collections.emptyList();
	}
}

