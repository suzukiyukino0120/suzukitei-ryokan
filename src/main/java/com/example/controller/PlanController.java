package com.example.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Reservation;
import com.example.domain.ReservationCalender;
import com.example.domain.Plan;
import com.example.domain.Room;
import com.example.form.SearchPlanForm;
import com.example.service.PlanService;

@Controller
@RequestMapping("")
public class PlanController {
	
	@ModelAttribute
	public SearchPlanForm setUpSearchForm() {
		return new SearchPlanForm();
	}
	
	@Autowired 
	private HttpSession session;
	
	@Autowired
	private PlanService planService;
	
	@RequestMapping("/top")
	public String top() {
		return "top";
	}
	
	@RequestMapping("/toPlanList")
	public String toPlanList() {
		session.removeAttribute("planList");
		return "plan_list";
	}
	
	@RequestMapping("/searchPlan")
	public String search(SearchPlanForm form, Model model) {
		
		LocalDate today = LocalDate.now();
		if(form.getDate() != null) {
			if(today.compareTo(form.getDate()) >= 0) {
				model.addAttribute("dateError", "–¾“úˆÈ~‚Ì“ú•t‚ğ“ü—Í‚µ‚Ä‚­‚¾‚³‚¢");
				return "plan_list";
			}
		}
		
		//‘O‚ÌŒŸõŒ‹‰Ê‚ğÁ‚·
		session.removeAttribute("planList");
		session.removeAttribute("totalPriceList");
		session.removeAttribute("stayDays");
		session.removeAttribute("numOfGuest");
		session.removeAttribute("checkinDate");

		List<Plan> planList = new ArrayList();
		
		
		if(form.getDate() != null){
			planList= 
					planService.searchPlanByDate(form.getSmoking(), form.getBathroom(), form.getBreakfast(), form.getDinner(), form.getDate(), form.getStayDays()-1, form.getNumOfGuest());
			
			//‹óº‚ ‚éƒvƒ‰ƒ“œ‚­
			Iterator<Plan> it = planList.iterator();
			while(it.hasNext()){
				Plan plan = it.next();
				if(plan.getRoom().getReservationCalender().size()<form.getStayDays()) {
					it.remove();
				}
			
			session.setAttribute("checkinDate", form.getDate());
			}
		}else {
			planList=
					planService.searchPlan(form.getSmoking(), form.getBathroom(), form.getBreakfast(), form.getDinner(), form.getPlanId(), form.getNumOfGuest());
		}
		
			List<Integer> totalPriceList = new LinkedList<>();
			
				for(Plan plan:planList) {
					Integer totalprice;
					
					if(form.getNumOfGuest() == 1) {
						if(form.getDate() != null || (form.getDate() == null && form.getStayDays() !=1)) {
							
							totalprice 
								= plan.getBasicCharge() * form.getStayDays();
							
						}else{
						
							totalprice 
								= plan.getBasicCharge();
						}
					}else {
						if(form.getDate() != null || (form.getDate() == null && form.getStayDays() !=1)) {
							
							totalprice 
								= (plan.getBasicCharge() + plan.getAdditionalCharge() * form.getNumOfGuest()) * form.getStayDays();
							
						}else {
						totalprice 
							= plan.getBasicCharge() + plan.getAdditionalCharge() * form.getNumOfGuest();
						}
					}
					totalPriceList.add(totalprice);

				}
			

			session.setAttribute("planList", planList);
			session.setAttribute("totalPriceList", totalPriceList);
			session.setAttribute("stayDays", form.getStayDays());
			session.setAttribute("numOfGuest", form.getNumOfGuest());
		
		return "plan_list";
		
	}

}
