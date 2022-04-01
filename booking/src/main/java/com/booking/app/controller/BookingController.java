package com.booking.app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.app.dto.BookingRequestDTO;
import com.booking.app.entity.Booking;
import com.booking.app.model.Train;
import com.booking.app.model.User;
import com.booking.app.service.BookingService;


@RestController
@RequestMapping("/booking")
public class BookingController {
		
	private static final Logger logger = Logger.getLogger(BookingController.class);
	
	{DOMConfigurator.configure("src/main/resources/log4j.xml");}
	
	@Autowired
	BookingService bookingService;
	
	@GetMapping("/{userId}")
	public List<Booking> getBookingsByUserId(@PathVariable("userId") Integer userId){
		logger.info("Getting all the booking of the user: " + userId);
		return bookingService.getBookingByUserId(userId);
	}
	
	@GetMapping("/bookings")
	public List<Booking> getBooking(){
		logger.info("Getting all the bookings in the database");
		return bookingService.getBookings();
	}
	
	@GetMapping("/users")
	public List<User> getUser(){
		logger.info("Getting all the users info in the booking service");
		return bookingService.getUsersService();
	}
	
	@GetMapping("/trains")
	public List<Train> getTrains(){
		logger.info("Getting all the trains info in the booking service");
		return bookingService.getTrainsService();
	}

	@GetMapping("/journeys")
	public List<Train> getTrainsPerParams(@RequestParam("source") String source, @RequestParam("destination") String destination, @RequestParam("date") String date){
		logger.info("Getting the train info with the parameters Source: " + source + " Destination: " + destination + " Date: " + date);
		LocalDate localDate = LocalDate.parse(date);
		return bookingService.getTrainsPerParams(source, destination, localDate);
	}
	
	@GetMapping("/train/{trainId}")
	public Optional<Train> getTrainsByTrainId(@PathVariable("trainId") Integer trainId){
		logger.debug("Getting train info of: " + trainId);
		return bookingService.getTrain(trainId);
	}
	
	@GetMapping("/user/{userId}")
	public Optional<User> getUserByUserId(@PathVariable("userId") Integer userId){
		logger.debug("Getting user info of: " + userId);
		return bookingService.getUser(userId);
	}
	
	@PostMapping("/savebooking")
	public ResponseEntity<String> saveBooking(@RequestBody BookingRequestDTO bookingRequestDTO){
		logger.debug("Saving the booking with the userId " + bookingRequestDTO.getUserId() + " and with the trainId: " + bookingRequestDTO.getTrainId());
		bookingService.saveBooking(bookingRequestDTO);
		return new ResponseEntity<String>("Booking Saved", HttpStatus.ACCEPTED);
	}
}
