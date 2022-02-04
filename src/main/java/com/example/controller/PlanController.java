package com.example.controller;

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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Plan;
import com.example.form.SearchPlanForm;
import com.example.service.PlanService;
import com.example.validator.SearchPlanValidator;

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
	
	@Autowired
	public SearchPlanValidator searchPlanValidator;
	
	@InitBinder("searchPlanForm")
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(searchPlanValidator);
    }
	
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
	public String search(@Validated SearchPlanForm form, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "plan_list";
		}
		
		//ëOÇÃåüçıåãâ Çè¡Ç∑
		session.removeAttribute("planList");
		session.removeAttribute("totalPriceList");
		session.removeAttribute("stayDays");
		session.removeAttribute("numOfGuest");
		session.removeAttribute("checkinDate");

		List<Plan> planList = new ArrayList();
		
		if(form.getDate() != null){
			planList= 
					planService.searchPlanWithDate(form.getSmoking(), form.getBathroom(), form.getBreakfast(), form.getDinner(), form.getDate(), form.getStayDays()-1, form.getNumOfGuest());
		}else {
			planList=
					planService.searchPlanWithoutDate(form.getSmoking(), form.getBathroom(), form.getBreakfast(), form.getDinner(), form.getPlanId(), form.getNumOfGuest());
		}
		
			List<Integer> totalPriceList = planService.calcTotalPrice(planList, form.getNumOfGuest(), form.getStayDays());

			session.setAttribute("planList", planList);
			session.setAttribute("totalPriceList", totalPriceList);
			session.setAttribute("stayDays", form.getStayDays());
			session.setAttribute("numOfGuest", form.getNumOfGuest());
			session.setAttribute("checkinDate", form.getDate());
		
		return "plan_list";
		
	}

}
