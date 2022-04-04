package com.user.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.user.app.client.TrainClient;
import com.user.app.dto.UserRequestDTO;
import com.user.app.repository.UserRepository;
import com.user.app.client.BookingClient;
import com.user.app.dto.LoginRequestDTO;
import com.user.app.dto.ResponseDTO;
import com.user.app.dto.TrainRequestDTO;
import com.user.app.entity.User;
import com.user.app.exception.LoginFailedException;
import com.user.app.exception.UserNotFoundException;
import com.user.app.model.Train;
import com.user.app.model.Booking;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@InjectMocks
	UserService userService;
	
	@Mock
	UserRepository userRepository; //Mock object
	
	@Mock
	BookingClient bookingClient;
	
	@Mock
	TrainClient trainClient;
	
	UserRequestDTO userRequestDTO;
	LoginRequestDTO loginRequestDTO;
	TrainRequestDTO trainRequestDTO;
	ResponseDTO responseDTO;
	User user;
	Booking booking;
	Train train;
	
	@BeforeEach
	public void setUp() {
		booking = new Booking();
		loginRequestDTO = new LoginRequestDTO();
		userRequestDTO = new UserRequestDTO();
		trainRequestDTO = new TrainRequestDTO();
		responseDTO = new ResponseDTO("User accepted", 200);
		user = new User();
		train = new Train();
		
		loginRequestDTO.setUsername("Diana Rodriguez");
		loginRequestDTO.setPassword("1234");
		
		userRequestDTO.setUsername("Diana Rodriguez");
		userRequestDTO.setPassword("1234");
		userRequestDTO.setEmail("diana@gmail.com");
		userRequestDTO.setPhoneNo("47959944");
		
		trainRequestDTO.setSource("colombia");
		trainRequestDTO.setDestination("mexico");
		trainRequestDTO.setDate("2022-02-02");
		
		train.setSource("colombia");
		train.setDestination("mexico");
		train.setDate(LocalDate.of(2022, 02, 02));
		booking.setBookId(1);
		booking.setUserId(1);
		user.setUserId(1);
		user.setUsername("Diana Rodriguez");
		user.setPassword("1234");
	}
	
	@Test
	@DisplayName("Find booking by user id: positive")
	public void getBookingsByUserId() {
		when(bookingClient.getBookingsByUserId(1)).thenReturn(List.of(booking));
		
		List<Booking> bookingList = bookingClient.getBookingsByUserId(1);
		assertNotNull(bookingList);
		assertEquals(1, bookingList.get(0).getBookId());
		assertEquals(1, bookingList.get(0).getUserId());
	}
	
	@Test
	@DisplayName("Find booking by user id: negative")
	public void getBookingByUserIdNegative() {
		when(bookingClient.getBookingsByUserId(1)).thenReturn(Collections.emptyList());
		
		List<Booking> bookingList = bookingClient.getBookingsByUserId(1);
		assertEquals(Collections.emptyList(), bookingList);
	}
	
	@Test
	@DisplayName("Find trains by source, destination and date: positive")
	public void getTrainsBySourceDestinationDate() {
		when(trainClient.getTrainsBySourceDestinationDate(trainRequestDTO)).thenReturn(List.of(train));
		
		List<Train> trainList = trainClient.getTrainsBySourceDestinationDate(trainRequestDTO);
		assertNotNull(trainList);
		assertEquals("colombia", trainList.get(0).getSource());
		assertEquals("mexico", trainList.get(0).getDestination());
	}
	
	@Test
	@DisplayName("Find trains by source, destination and date: negative")
	public void getTrainsBySourceDestinationDateNegative() {
		when(trainClient.getTrainsBySourceDestinationDate(trainRequestDTO)).thenReturn(Collections.emptyList());
		
		List<Train> trainList = trainClient.getTrainsBySourceDestinationDate(trainRequestDTO);
		assertEquals(Collections.emptyList(), trainList);
	}
	
	@Test
	@DisplayName("Find all users")
	public void getUsers() {
		when(userService.getUsers()).thenReturn(List.of(user));
		
		List<User> userList = userService.getUsers();
		assertNotNull(userList);
	}
	
	@Test
	@DisplayName("Find users by id: positive")
	public void findUserTest() {
		try {
		when(userService.getUserByUserId(1)).thenReturn(Optional.of(user));
		
		Optional<User> resultOptional = userService.getUserByUserId(1);
		assertNotNull(resultOptional);
		assertEquals(1, resultOptional.get().getUserId());
		} catch (UserNotFoundException e) {
			// TODO: handle exception
		}
	}
	

	@Test
	@DisplayName("Find users by id: negative")
	public void findUsersNegative() {
		try {
			when(userService.getUserByUserId(1)).thenReturn(Optional.empty());
			
			assertThrows(UserNotFoundException.class, () -> userService.getUserByUserId(1));
		} catch (UserNotFoundException e) {
			// TODO: handle exception
		}
	}
	
	@Test
	@DisplayName("login details: positive")
	public void login() {
		when(userRepository.findByUsernameAndPassword(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())).thenReturn(Optional.of(user));
	
		Optional<User> optionalUser = userRepository.findByUsernameAndPassword(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
		assertNotNull(optionalUser);
		assertEquals("Diana Rodriguez", optionalUser.get().getUsername());
		assertEquals("1234", optionalUser.get().getPassword());
	}
	
	@Test
	@DisplayName("login details: negative")
	public void loginNegative() {
		when(userRepository.findByUsernameAndPassword(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())).thenReturn(Optional.empty());
	
		assertThrows(LoginFailedException.class, () -> userService.login(loginRequestDTO));
	}
}