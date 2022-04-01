package com.train.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.train.app.dto.TrainRequestDTO;
import com.train.app.entity.Train;
import com.train.app.repository.TrainRepository;

@Service
public class TrainService {
	@Autowired
	TrainRepository trainRepository;
	
	public List<Train> getTrains(){
		return trainRepository.findAll();
	}
	
	public Train saveTrains(Train train) {
		return trainRepository.save(train);
	}
	
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
	
	public Optional<Train> getTrainByTrainId(Integer trainId){
		return trainRepository.findByTrainId(trainId);
	}
	
	
	
	
	
	public List<Train> getTrainsBySourceDestinationDate(TrainRequestDTO trainRequestDTO){
		return trainRepository.findBySourceAndDestinationAndDate(trainRequestDTO.getSource(), trainRequestDTO.getDestination(), LocalDate.parse(trainRequestDTO.getDate()));
	}

}
