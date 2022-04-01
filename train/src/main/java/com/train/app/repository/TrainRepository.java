package com.train.app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.train.app.entity.Train;

public interface TrainRepository extends CrudRepository<Train, Integer>{
	
	List<Train> findAll();
	
	public abstract List<Train> findByDestination(String destination);
	
	List<Train> findBySourceAndDestinationAndDate(String source, String destination, LocalDate date);
	
	public abstract Optional<Train> findByTrainId(Integer trainId);
}
