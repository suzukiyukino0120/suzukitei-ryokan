package com.example.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class SearchReservationForm {
	
	private String name;
	
	private String kana;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	private Integer payMethod;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKana() {
		return kana;
	}

	public void setKana(String kana) {
		this.kana = kana;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	@Override
	public String toString() {
		return "SearchReservationForm [date=" + date + ", name=" + name + ", kana=" + kana + ", payMethod=" + payMethod
				+ "]";
	}
	

}
