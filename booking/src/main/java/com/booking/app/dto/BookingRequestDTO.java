package com.booking.app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookingRequestDTO {
	
	@NotNull
	private Integer userId;
	@NotNull
	private Integer trainId;
	@Size(min = 1, message = "Tickets Quantity Should Not Be Empty")
	private int ticketsQuantity;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTrainId() {
		return trainId;
	}
	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}
	public int getTicketsQuantity() {
		return ticketsQuantity;
	}
	public void setTicketsQuantity(int ticketsQuantity) {
		this.ticketsQuantity = ticketsQuantity;
	}
}
