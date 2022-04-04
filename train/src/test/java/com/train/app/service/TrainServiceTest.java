package com.train.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
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

import com.train.app.dto.TrainRequestDTO;
import com.train.app.entity.Train;
import com.train.app.exception.TrainNotFoundException;
import com.train.app.repository.TrainRepository;
import com.train.app.service.TrainService;

@ExtendWith(MockitoExtension.class)
public class TrainServiceTest {
	
	@InjectMocks
	TrainService trainService;
	
	@Mock
	TrainRepository trainRepository; //Mock object
	
	TrainRequestDTO trainRequestDTO;
	Train train;
	
	@BeforeEach
	public void setUp() {
		trainRequestDTO = new TrainRequestDTO();
		train = new Train();
		
		trainRequestDTO.setSource("colombia");
		trainRequestDTO.setDestination("mexico");
		trainRequestDTO.setDate("2022-02-02");
		
		train.setSource("colombia");
		train.setDestination("mexico");
		train.setDate(LocalDate.of(2022, 02, 02));
		train.setTrainId(1);
	}
	
	
	@Test
	@DisplayName("Save trains details: positive")
	public void saveTrainTest() {
		when(trainRepository.save(any(Train.class))).thenAnswer(i -> {
			Train train = i.getArgument(0);
			train.setTrainId(1);
			return train;
		});
		
		Train trainResult = trainService.saveTrains(trainRequestDTO);
		assertNotNull(trainResult);
		assertEquals(1, trainResult.getTrainId());
	}
	
	@Test
	@DisplayName("Find train by id: positive")
	public void getTrainByTrainId() {
		try {
			when(trainService.getTrainByTrainId(1)).thenReturn(Optional.of(train));
			
			Optional<Train> optionalTrain = trainService.getTrainByTrainId(1);
			assertNotNull(optionalTrain);
			assertEquals(1, optionalTrain.get().getTrainId());
		} catch (TrainNotFoundException ex) {
			// TODO: handle exception
		}
	}
	
	@Test
	@DisplayName("Find train by id: negative")
	public void saveTrainsNegative() {
		try {
			when(trainService.getTrainByTrainId(1)).thenReturn(Optional.empty());
			
		} catch (TrainNotFoundException e) {
			assertThrows(TrainNotFoundException.class, () -> trainService.getTrainByTrainId(1));
		}
	}
}