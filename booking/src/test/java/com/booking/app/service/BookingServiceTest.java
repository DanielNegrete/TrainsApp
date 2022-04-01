package com.booking.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.app.client.TrainClient;
import com.booking.app.client.UserClient;
import com.booking.app.dto.BookingRequestDTO;
import com.booking.app.entity.Booking;
import com.booking.app.exception.TrainNotFoundException;
import com.booking.app.exception.UserNotFoundException;
import com.booking.app.model.Train;
import com.booking.app.model.User;
import com.booking.app.repository.BookingRepo;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
	
	@Mock
	BookingRepo bookingRepo;
	
	@Mock
	UserClient userClient;
	
	@Mock
	TrainClient trainClient;
	
	@InjectMocks
	BookingService bookingService;
	
	BookingRequestDTO bookingRequestDTO;
	Booking booking;
	User user;
	Train train;
	
	@BeforeEach
	public void setUp() {
		bookingRequestDTO = new BookingRequestDTO();
		booking = new Booking();
		user = new User();
		train = new Train();
		
		booking.setBookId(1);
		booking.setUserId(1);
		booking.setTrainId(1);
		booking.setTicketsQuantity(2);
		booking.setTotalPrice(200);
		
		bookingRequestDTO.setUserId(1);
		bookingRequestDTO.setTrainId(1);
		bookingRequestDTO.setTicketsQuantity(2);
		
		user.setUserId(1);
		train.setTrainId(1);
		train.setPrice(100.00);
	}
	
	@Test
	@DisplayName("Save booking details: positive")
	public void saveBookingDetailsTest() {
		when(userClient.getUserByUserId(1)).thenReturn(Optional.of(user));
		
		when(trainClient.getTrainByTrainId(1)).thenReturn(Optional.of(train));
		
		when(bookingRepo.save(any(Booking.class))).then(i -> {
			Booking book = i.getArgument(0);
			book.setBookId(1);
			book.setTotalPrice(200);
			return book;
		});
		
		Booking bookingResult = bookingService.saveBooking(bookingRequestDTO);
		assertNotNull(bookingResult);
		assertEquals(1, bookingResult.getBookId());
		assertEquals(1, bookingResult.getUserId());
		assertEquals(1, bookingResult.getTrainId());
	}
	
	@Test
	@DisplayName("Save booking details negative")
	public void saveBookingDetailsNegative() {
		when(userClient.getUserByUserId(1)).thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class, () -> bookingService.saveBooking(bookingRequestDTO));
	}
	
	@Test
	@DisplayName("Save booking details negative")
	public void saveBookingDetailsNegative2() {
		when(userClient.getUserByUserId(1)).thenReturn(Optional.of(user));
		
		when(trainClient.getTrainByTrainId(1)).thenReturn(Optional.empty());
		
		assertThrows(TrainNotFoundException.class, () -> bookingService.saveBooking(bookingRequestDTO));
	}
	
	@Test
	@DisplayName("Get all bookings by userId: positive")
	public void getBookingByUserId() {
		when(userClient.getUserByUserId(1)).thenReturn(Optional.of(user));
		
		List<Booking> bookingResult = bookingService.getBookingByUserId(1);
		
		assertNotNull(bookingResult);
	}
	
	@Test
	@DisplayName("Get all bookings by userId: negative")
	public void getBookingByUserIdNegative() {
		when(userClient.getUserByUserId(1)).thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class, () -> bookingService.getBookingByUserId(1));
	}
}
