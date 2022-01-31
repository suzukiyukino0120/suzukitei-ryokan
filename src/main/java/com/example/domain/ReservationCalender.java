package com.example.domain;

import java.time.LocalDate;

public class ReservationCalender {
	
	private LocalDate date;
	
	private Integer roomId;
	
	private Integer reservedRoom;
	
	private Integer reservableRoom;
	
	private String empty;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}


	public Integer getReservedRoom() {
		return reservedRoom;
	}

	public void setReservedRoom(Integer reservedRoom) {
		this.reservedRoom = reservedRoom;
	}

	public Integer getReservableRoom() {
		return reservableRoom;
	}

	public void setReservableRoom(Integer reservableRoom) {
		this.reservableRoom = reservableRoom;
	}

	public String getEmpty() {
		return empty;
	}

	public void setEmpty(String empty) {
		this.empty = empty;
	}

	@Override
	public String toString() {
		return "ReservationCalender [date=" + date + ", roomId=" + roomId + ", reservedRoom=" + reservedRoom
				+ ", reservableRoom=" + reservableRoom + ", empty=" + empty + "]";
	}


	
	
	
	
	
	

}
