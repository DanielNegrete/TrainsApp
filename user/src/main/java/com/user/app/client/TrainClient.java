package com.user.app.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.app.dto.TrainRequestDTO;
import com.user.app.model.Train;

@FeignClient(name = "trainuser", url = "http://localhost:9092")
public interface TrainClient {
	
	@PostMapping("/train/trainssource")
	List<Train> getTrainsBySourceDestinationDate(@RequestBody TrainRequestDTO trainRequestDTO);
}
