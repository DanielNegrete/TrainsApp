package com.train.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.train.app.dto.TrainRequestDTO;
import com.train.app.entity.Train;
import com.train.app.exception.JourneyNotfoundException;
import com.train.app.exception.TrainNotFoundException;
import com.train.app.repository.TrainRepository;

@Service
public class TrainService {
	@Autowired
	TrainRepository trainRepository;
	
	public List<Train> getTrains(){
		return trainRepository.findAll();
	}
	
	public Train saveTrains(TrainRequestDTO trainRequestDTO) {
		Train train = new Train();
		BeanUtils.copyProperties(trainRequestDTO, train);
		return trainRepository.save(train);
	}
	
	public Optional<Train> getTrainByTrainId(Integer trainId){
		Optional<Train> optionalTrain = trainRepository.findByTrainId(trainId);
		if(!optionalTrain.isPresent())
			throw new TrainNotFoundException("Train not found");
		return optionalTrain;
	}
	
	public List<Train> getTrainsBySourceDestinationDate(TrainRequestDTO trainRequestDTO){
		List<Train> trainList = trainRepository.findBySourceAndDestinationAndDate(trainRequestDTO.getSource(), trainRequestDTO.getDestination(), LocalDate.parse(trainRequestDTO.getDate()));
		if(trainList.isEmpty())
			throw new JourneyNotfoundException("Journey not found");
		return trainList;
	}

	
	
	
	//
	/*
	 	public Optional<Train> getById(Integer trainId){
		return trainRepository.findById(trainId);
	}
	
	
	public List<Train> getByDestination(String destination){
		return trainRepository.findByDestination(destination);
	}
	
	public boolean deleteTrain(Integer trainId) {
		try {
			trainRepository.deleteById(trainId);
			return true;
		}catch(Exception err) {
			return false;
		}
	}
	
	public List<Train> getTrainsPerParams(String source, String destination, LocalDate date){
		return trainRepository.findBySourceAndDestinationAndDate(source, destination, date);
	}
	
	 */
}
