package com.train.app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.train.app.dto.TrainRequestDTO;
import com.train.app.entity.Train;
import com.train.app.service.TrainService;

@RestController
@RequestMapping("/train")
@Validated
public class TrainController {
	
	private static final Logger logger = Logger.getLogger(TrainController.class);
	
	{DOMConfigurator.configure("src/main/resources/log4j.xml");}
	
	@Autowired
	TrainService trainService;
	
	@GetMapping()
	public List<Train> getTrains(){
		logger.info("Getting all the trains info");
		return trainService.getTrains();
	}
	
	@PostMapping()
	public Train saveTrain(@Valid @RequestBody TrainRequestDTO trainRequestDTO) {
		logger.debug("Train: " + " got saved");
		return this.trainService.saveTrains(trainRequestDTO);
	}


    @GetMapping("/{trainId}")
    public Optional<Train> getTrainByTrainId(@NotNull @Min(1) @PathVariable("trainId") Integer trainId){
    	logger.info("Getting the train info of: " + trainId);
    	return trainService.getTrainByTrainId(trainId);
    }
    
    @PostMapping("/trainssource")
    public List<Train> getTrainsBySourceDestinationDate(@Valid @RequestBody TrainRequestDTO trainRequestDTO){
    	return trainService.getTrainsBySourceDestinationDate(trainRequestDTO);
    }
    
    
    
    
    
    
    //
    /*
         @GetMapping("/journeys")
    public List<Train> getJourneys(@RequestParam("source") String source, @RequestParam("destination") String destination, @RequestParam("date") String date) {
    	LocalDate localDate = LocalDate.parse(date);
    	logger.info("Getting the train info using the parameters Source:" + source + " Destination: " + destination + " Date: " + date);
    	return trainService.getTrainsPerParams(source, destination, localDate);
    }
    
        
    @DeleteMapping( path = "/{trainId}")
    public String deleteById(@PathVariable("trainId") Integer trainId){
        boolean ok = this.trainService.deleteTrain(trainId);
        if (ok){
        	logger.debug("Train: " + trainId + " got deleted");
            return "Delete Train Successful id:" + trainId;
        }else{
        	logger.error("Error occurred while deleting the train: " + trainId);
            return "Cannot Deleted Train:" + trainId;
        }
    }  
    
        
    @GetMapping("/query")
    public List<Train> getTrainsByDestination(@RequestParam("destination") String destination){
        return this.trainService.getByDestination(destination);
    } 
    
        @GetMapping( path = "/{trainId}")
    public Optional<Train> getTrainsById(@PathVariable("trainId") Integer trainId) {
        return this.trainService.getById(trainId);
    }
     */
    
}
