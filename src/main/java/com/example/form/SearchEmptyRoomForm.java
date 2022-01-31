package com.example.form;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class SearchEmptyRoomForm {
	@NotNull(message="�n�܂�̓��t����͂��Ă�������")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate startDate;
	
	@NotNull(message="�I���̓��t����͂��Ă�������")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate endDate;
	

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	
	

	@Override
	public String toString() {
		return "SearchEmptyRoomForm [startDate=" + startDate + ", endDate=" + endDate +"]";
	}

	
	
	

}
