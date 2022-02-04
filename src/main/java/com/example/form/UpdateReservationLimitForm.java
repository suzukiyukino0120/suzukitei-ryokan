package com.example.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class UpdateReservationLimitForm {
	private Integer limitOfRoom1;
	
	private Integer limitOfRoom2;
	
	private Integer limitOfRoom3;
	
	private Integer limitOfRoom4;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	public Integer getLimitOfRoom1() {
		return limitOfRoom1;
	}

	public void setLimitOfRoom1(Integer limitOfRoom1) {
		this.limitOfRoom1 = limitOfRoom1;
	}

	public Integer getLimitOfRoom2() {
		return limitOfRoom2;
	}

	public void setLimitOfRoom2(Integer limitOfRoom2) {
		this.limitOfRoom2 = limitOfRoom2;
	}

	public Integer getLimitOfRoom3() {
		return limitOfRoom3;
	}

	public void setLimitOfRoom3(Integer limitOfRoom3) {
		this.limitOfRoom3 = limitOfRoom3;
	}

	public Integer getLimitOfRoom4() {
		return limitOfRoom4;
	}

	public void setLimitOfRoom4(Integer limitOfRoom4) {
		this.limitOfRoom4 = limitOfRoom4;
	}
	
	

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "UpdateReservableLimitForm [limitOfRoom1=" + limitOfRoom1 + ", limitOfRoom2=" + limitOfRoom2
				+ ", limitOfRoom3=" + limitOfRoom3 + ", limitOfRoom4=" + limitOfRoom4 + ", date=" + date + "]";
	}

	
	

}