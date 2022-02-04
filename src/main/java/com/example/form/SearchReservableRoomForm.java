package com.example.form;

import javax.validation.constraints.NotBlank;

public class SearchReservableRoomForm {
	
	@NotBlank(message="”NŒŽ‚ð‘I‘ð‚µ‚Ä‚­‚¾‚³‚¢")
	 String month;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	
	

}
