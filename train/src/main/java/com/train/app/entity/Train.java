package com.train.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "train")
public class Train {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
	private Integer trainId;
	private String source;
	private String destination;
	private LocalDate date;
	private LocalTime timeLeaves;
	private LocalTime timeArrive;
	private Double price;
	private String rail;
	public Integer getTrainId() {
		return trainId;
	}
	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTimeLeaves() {
		return timeLeaves;
	}
	public void setTimeLeaves(LocalTime timeLeaves) {
		this.timeLeaves = timeLeaves;
	}
	public LocalTime getTimeArrive() {
		return timeArrive;
	}
	public void setTimeArrive(LocalTime timeArrive) {
		this.timeArrive = timeArrive;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getRail() {
		return rail;
	}
	public void setRail(String rail) {
		this.rail = rail;
	}
	
}
