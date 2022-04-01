package com.booking.app.client;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.booking.app.model.Train;

@FeignClient(name = "train", url = "http://localhost:9092")
public interface TrainClient {
	
	@GetMapping("/journeys")
	List<Train> getTrainsPerParams(@RequestParam("source") String source, @RequestParam("destination") String destination, @RequestParam("date") LocalDate date); 
	
	@GetMapping("/train")
	List<Train> getTrains();
	
	@GetMapping("/train/{trainId}")
	Optional<Train> getTrainByTrainId(@PathVariable("trainId") Integer trainId);
}
