package com.example.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.domain.Administrator;
import com.example.domain.Plan;
import com.example.form.AdminLoginForm;
import com.example.form.AdministratorForm;
import com.example.form.ReservationForm;
import com.example.service.AdministratorService;
import com.example.service.EmployeeService;
import com.example.service.ReservationService;

@Component
public class ReservationValidator implements Validator{
	
	@Autowired
	private ReservationService reservationService;
	
	
	@Override
    public boolean supports(Class<?> clazz) {

		return ReservationForm.class.isAssignableFrom(clazz);
    }
	
	@Override
	    public void validate(Object form, Errors errors) {
		ReservationForm validationForm = (ReservationForm)form;
		
		Plan planInfo = reservationService.checkGuestCapaAndCharge(validationForm.getPlanId());
		
		Integer guestCapacity = planInfo.getRoom().getGuestCapacity();
		
		if(validationForm.getNumOfGuest() != null) {
			if(guestCapacity <= validationForm.getNumOfGuest()) {
				errors.rejectValue("numOfGuest", "", "���̂�������"+ guestCapacity +"�l�����ł�");
			}
		}
		
		LocalDate today = LocalDate.now();
		if(validationForm.getCheckinDate() != null) {
			if(today.compareTo(validationForm.getCheckinDate()) >= 0) {
				errors.rejectValue("checkinDate", "", "�h�����͖����ȍ~�ɂ��Ă�������");
			}
		
		
		
	}




	}
	}
