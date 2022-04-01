package com.user.app.controller;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.app.dto.LoginRequestDTO;
import com.user.app.dto.ResponseDTO;
import com.user.app.dto.TrainRequestDTO;
import com.user.app.entity.User;
import com.user.app.model.Booking;
import com.user.app.model.Train;
import com.user.app.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	{DOMConfigurator.configure("src/main/resources/log4j.xml");}
	
	@Autowired
	UserService userService;
	
	@GetMapping()
	public List<User> getUsers(){
		logger.info("Getting all the users");
		return userService.getUsers();
	}
	
	@PostMapping("/save")
	public User saveUser(@RequestBody User user) {
		logger.debug("User: " + user.getUsername() + " got saved");
		return this.userService.saveUsers(user);
	}

    @GetMapping( path = "/{userId}")
    public Optional<User> getUsersById(@PathVariable("userId") Integer userId) {
    	logger.info("Getting the info of user: " + userId);
        return this.userService.getById(userId);
    }
    
    @GetMapping("/query")
    public Optional<User> getUserByUsername(@RequestParam("username") String username){
    	logger.info("Getting the info of the user: " + username);
        return userService.getByUsername(username);
    } 
    
    @DeleteMapping( path = "/{userId}")
    public String deleteById(@PathVariable("userId") Integer userId){
        boolean ok = this.userService.deleteUser(userId);
        if (ok){
        	logger.debug("User: " + userId + " got deleted");
            return "Delete User Successful id:" + userId;
        }else{
        	logger.error("Error occurred while deleting user: " + userId);
            return "Cannot Deleted User:" + userId;
        }
    }  
    
    @GetMapping("/user/{userId}")
    public Optional<User> validateUser(@PathVariable("userId") Integer userId) {
    	logger.info(" Validating if the user: " + userId + " exist or not");
    	return userService.getUserByUserId(userId);
    }
    
    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> login(LoginRequestDTO loginRequestDTO){
    	ResponseDTO response = userService.login(loginRequestDTO);
    	return new ResponseEntity<ResponseDTO>(response, HttpStatus.ACCEPTED);
    }
    
    @GetMapping("/booking/{userId}")
    public List<Booking> getBookingsByUserId(@PathVariable("userId") Integer userId){
    	logger.info("Getting the bookings of the user: " + userId);
    	return userService.getBookings(userId);
    }
    
    
    @PostMapping("/train/trainssource")
    public List<Train> getTrainsBySourceDestinationDate(@RequestBody TrainRequestDTO trainRequestDTO){
    	return userService.getTrainsBySourceDestinationDate(trainRequestDTO);
    }
    
}
