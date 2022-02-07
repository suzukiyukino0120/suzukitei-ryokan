package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Reservation;
import com.example.domain.Plan;
import com.example.form.ReservationForm;
import com.example.service.ReservationCalenderService;
import com.example.service.ReservationService;
import com.example.validator.ReservationValidator;


@Controller
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired 
	private HttpSession session;
	
	@Autowired
	public ReservationValidator reservationValidator;
	
	@InitBinder("reservationForm")
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(reservationValidator);
    }
	
	@ModelAttribute
	public ReservationForm setUpReservationForm() { 
		
		//���͊m�F��ʂ�����͉�ʂɖ߂������Ƀt�H�[���ɕ\�������悤�ɂ���
		ReservationForm form = new ReservationForm();
		if(session.getAttribute("reservation") != null){
			Reservation reservation = (Reservation) session.getAttribute("reservation");
			BeanUtils.copyProperties(reservation, form);
		}
		
		return form;
	}
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private ReservationCalenderService reservationCalenderService;
	
	@RequestMapping("/loadReservationPage")
	public String loadReservationPage() {
		return "reservation";
	}
	
	@RequestMapping("/toReservation")
	public String toReservation(Integer planListNum,
							@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkinDate, Model model) {
		
		if(planListNum != null) {
			List<Plan> planList = (List<Plan>) session.getAttribute("planList");
			session.setAttribute("plan", planList.get(planListNum));
		}
		
		//���������Ŏw�肵���h���l���A�h�������A�`�F�b�N�C������\��t�H�[���ɓ��͍ς݂ɂ���
		ReservationForm form = new ReservationForm();
		form.setNumOfGuest((Integer) session.getAttribute("numOfGuest"));
		form.setStayDays((Integer) session.getAttribute("stayDays"));
		if(checkinDate != null) {
			form.setCheckinDate(checkinDate);
		}else {
			form.setCheckinDate((LocalDate) session.getAttribute("checkinDate"));
		}
		model.addAttribute("reservationForm", form);
		
		return "reservation";
	}
	
	@RequestMapping("/confirmReservationForm")
	public String reservation(@Validated ReservationForm form, BindingResult result,  Model model) {
		if(result.hasErrors()) {
			return "reservation";
		}
		
		Reservation reservation = new Reservation();
		BeanUtils.copyProperties(form, reservation);
		
		//���v���z�v�Z
		Plan chargeInfo = reservationService.checkCharge(form.getPlanId());
		reservation.setTotalPrice(reservation.calcTotalPrice(form.getNumOfGuest(), form.getStayDays(), chargeInfo.getAdditionalCharge(), chargeInfo.getBasicCharge()));
		
		session.setAttribute("reservation", reservation);
		
		return "reservation_confirm";
	}
	
	
	@RequestMapping("/complete")
	public String completeReservation(ReservationForm form, Model model) {
		
		//�󎺌����i�\��t�H�[���œ��t�ς����l�A�󎺌������ĂȂ��l�A�t�H�[�����͒��ɗ\��g���܂�\�����l���j
		Plan plan = (Plan) session.getAttribute("plan");
		Integer roomId = plan.getRoomId();
		String fullReservationMsg
		= reservationService.checkFullReservation(form.getCheckinDate(), form.getCheckinDate().plusDays(form.getStayDays()-1), roomId);
		
		//�����̎��G���\�y�[�W�\��
		if(!"".equals(fullReservationMsg)) {
			model.addAttribute("fullReservationMsg", fullReservationMsg);
			return "full_reservation";
		}

		//�\��e�[�u���X�V
		Reservation reservation = (Reservation) session.getAttribute("reservation");
		reservationService.insert(reservation);
		
		//�\��J�����_�[�X�V
		reservationCalenderService.updateReservationCalender(form.getCheckinDate(), form.getStayDays()-1, roomId);
		
		return "redirect:/reservation/toComplete";
	}
	
	@RequestMapping("/toComplete")
	public String toComplete() {
		return "reservation_complete";
	}
	
}
