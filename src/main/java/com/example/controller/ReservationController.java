package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.domain.Reservation;
import com.example.domain.ReservationCalender;
import com.example.domain.Plan;
import com.example.domain.Room;
import com.example.form.ReservationForm;
import com.example.service.ReservationCalenderService;
import com.example.service.ReservationService;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired 
	private HttpSession session;
	
	@ModelAttribute
	public ReservationForm setUpReservationForm() { 
		
		//入力確認画面から入力画面に戻った時にフォームに表示されるようにする
		ReservationForm form = new ReservationForm();
		if(session.getAttribute("reservation") != null){
			Reservation reservation = (Reservation) session.getAttribute("reservation");
			form.setName(reservation.getName());
			form.setKana(reservation.getKana());
			form.setZipcode(reservation.getZipcode());
			form.setAddress(reservation.getAddress());
			form.setTelephone(reservation.getTelephone());
			form.setEmail(reservation.getEmail());
			form.setCheckinDate(reservation.getCheckinDate());
			form.setStayDays(reservation.getStayDays());
			form.setNumOfGuest(reservation.getNumOfGuest());
			form.setPayMethod(String.valueOf(reservation.getPayMethod()));
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
			System.out.println(planList.get(planListNum));
		
		}
		
		//検索条件で指定した宿泊人数、宿泊日数、チェックイン日を予約フォームに入力済みにする
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
		
		Plan plan = (Plan) session.getAttribute("plan");
		
		Plan planInfo = reservationService.checkGuestCapaAndCharge(plan.getId());
		
		Integer guestCapacity = planInfo.getRoom().getGuestCapacity();
		
		
		if(result.hasErrors()) {
			if(form.getNumOfGuest() != null) {
				if(guestCapacity <= form.getNumOfGuest()) {
					model.addAttribute("numOfGuestError", "このお部屋は"+ guestCapacity +"人部屋です");
				}
			}
			
			LocalDate today = LocalDate.now();
			if(form.getCheckinDate() != null) {
				if(today.compareTo(form.getCheckinDate()) >= 0) {
					model.addAttribute("checkinDateError", "宿泊日は明日以降にしてください");
				}
			}
			return "reservation";
		}
		if(guestCapacity != null) {
			if(guestCapacity < form.getNumOfGuest()) {
				model.addAttribute("numOfGuestError", "このお部屋は"+ guestCapacity +"人部屋です");
				
				return "reservation";
			}
		}
		
		LocalDate today = LocalDate.now();
		if(form.getCheckinDate() != null) {
			if(today.compareTo(form.getCheckinDate()) >= 0) {
				model.addAttribute("checkinDateError", "宿泊日は明日以降にしてください");
				
				return "reservation";
			}
		}
		
		Reservation reservation = new Reservation();
		reservation.setPlanId(plan.getId());
		reservation.setName(form.getName());
		reservation.setKana(form.getKana());
		reservation.setZipcode(form.getZipcode());
		reservation.setAddress(form.getAddress());
		reservation.setTelephone(form.getTelephone());
		reservation.setEmail(form.getEmail());
		reservation.setCheckinDate(form.getCheckinDate());
		reservation.setStayDays(form.getStayDays());
		reservation.setNumOfGuest(form.getNumOfGuest());
		reservation.setPayMethod(Integer.parseInt(form.getPayMethod()));
		
		//合計金額計算
		reservation.setTotalPrice(reservation.calcTotalPrice(form.getNumOfGuest(), form.getStayDays(), 
																planInfo.getAdditionalCharge(), planInfo.getBasicCharge()));
		
		session.setAttribute("reservation", reservation);
		session.setAttribute("plan", plan);
		
		return "reservation_confirm";
	}
	
	
	@RequestMapping("/complete")
	public String completeReservation(ReservationForm form, Model model) {
		
		//空室検索（予約フォームで日付変えた人、空室検索してない人、フォーム入力中に予約枠埋まる可能性を考慮）
		Plan plan = (Plan) session.getAttribute("plan");
		Integer roomId = plan.getRoomId();
		
		List<ReservationCalender> reservationInfo 
		= reservationCalenderService.searchReservedRoom(form.getCheckinDate(), form.getCheckinDate().plusDays(form.getStayDays()-1), roomId);

		String fullReservationMsg = null;//満室時のエラー文　
		for(ReservationCalender day: reservationInfo) {
			if(day.getReservedRoom() >= day.getReservableRoom()) {
				fullReservationMsg += day.getDate()+ "  ";
			}
		}
		if(fullReservationMsg != null) {
			model.addAttribute("fullReservationMsg", fullReservationMsg);
			return "full_reservation";
		}

		//予約テーブル更新
		Reservation reservation = (Reservation) session.getAttribute("reservation");
		reservationService.insert(reservation);
		
		//予約カレンダー更新
		reservationCalenderService.updateReservationCalender(form.getCheckinDate(), form.getStayDays()-1, roomId);
		
		return "redirect:/reservation/toComplete";
	}
	
	@RequestMapping("/toComplete")
	public String toComplete() {
		return "reservation_complete";
	}
	
}
