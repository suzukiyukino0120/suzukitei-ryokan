package com.example.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Plan;
import com.example.domain.Reservation;
import com.example.form.CreatePlanForm;
import com.example.form.SearchReservationForm;
import com.example.form.UpdatePlanForm;
import com.example.service.ManageService;

@Controller
@RequestMapping("/manage")
public class ManageController {
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public CreatePlanForm setUpCreatePlanForm() {
		return new CreatePlanForm();
	}
	
	@ModelAttribute
	public UpdatePlanForm setUpUpdatePlanForm() {
		return new UpdatePlanForm();
	}
	
	@ModelAttribute
	public SearchReservationForm setUpSearchReservationForm() {
		return new SearchReservationForm();
	}
	
	@Autowired
	private ManageService manageService;
	
	
	@RequestMapping("/toRegisteredPlanList")
	public String toRegisteredPlanList(Model model) {
		
		model.addAttribute("planList", manageService.findAllPlan());
		
		return"manage/registered_plan_list";
	}
	
	@RequestMapping("/toCreatePlan")
	public String toCreatePlan() {
		return "manage/create_plan";
	}
	
	@RequestMapping("/createPlan")
	public String createPlan(@Validated CreatePlanForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return"manage/create_plan";
		}
		
		Plan plan = new Plan();

		plan.setName(form.getName());
		plan.setRoomId(form.getRoomId());
		plan.setBreakfast(form.getBreakfast());
		plan.setDinner(form.getDinner());
		plan.setBasicCharge(form.getBasicCharge());
		plan.setAdditionalCharge(form.getAdditionalCharge());
		plan.setComment(form.getComment());

//		�摜�̖��O���擾�E�t�@�C���ɕۑ�
		MultipartFile multiFile = form.getImage();
		String imageName = multiFile.getOriginalFilename();
		
		File filepath = new File("src/main/resources/static/img/plans/"+imageName);
		try {
			byte[] bytes = multiFile.getBytes();
			FileOutputStream stream = new FileOutputStream(filepath.toString());
			stream.write(bytes);
			stream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		plan.setImage(imageName);
		
		manageService.insertPlan(plan);
		
		return"redirect:/manage/toRegisteredPlanList";
	}
	
	@RequestMapping("/toUpdatePlan")
	public String toUpdatePlan(Integer id, Model model) {
		
		Plan plan = manageService.findPlanById(id);
		
		//�ҏW�t�H�[���Ɍ��݂̓o�^���e�����͍ςɂȂ�悤�ɂ���
		UpdatePlanForm form = new UpdatePlanForm();
		form.setId(plan.getId());
		form.setName(plan.getName());
		form.setRoomId(plan.getRoomId());
		form.setBreakfast(plan.getBreakfast());
		form.setDinner(plan.getDinner());
		form.setBasicCharge(plan.getBasicCharge());
		form.setAdditionalCharge(plan.getAdditionalCharge());
		form.setComment(plan.getComment());
		form.setNowImage(0);
		
		model.addAttribute("updatePlanForm", form);
		session.setAttribute("image", plan.getImage());
		
		return "manage/update_plan";
	}
	
	@RequestMapping("/updatePlan")
	public String updatePlan(@Validated UpdatePlanForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			if(form.getNowImage() == 1) {
				if(form.getImage() == null) {
				model.addAttribute("imageError", "�C���[�W��ύX����ꍇ�̓C���[�W��Y�t���Ă��������B");
				System.out.println("1��"+form.getNowImage());
				System.out.println("1�ς���"+form.getImage());
				}
			}else {
				if(form.getImage() != null) {
				model.addAttribute("imageError", "�C���[�W��ύX���Ȃ��ꍇ�̓C���[�W��Y�t���Ȃ��ł�������");
				}
			}
		System.out.println(result);
			return "manage/update_plan";
	}
		
		if(form.getNowImage() == 1) {
			if(form.getImage() == null) {
				model.addAttribute("imageError", "�C���[�W��ύX����ꍇ�̓C���[�W��Y�t���Ă��������B");
				
				System.out.println("��"+form.getNowImage());
				System.out.println("�ς���"+form.getImage());
			
				return "manage/update_plan";
			}
		}else {
			if(form.getImage() != null) {
				model.addAttribute("imageError", "�C���[�W��ύX���Ȃ��ꍇ�̓C���[�W��Y�t���Ȃ��ł�������");
				
				System.out.println("2��"+form.getNowImage());
				System.out.println("2�ς���"+form.getImage());
				return "manage/update_plan";
			}
		}
				
		Plan plan = new Plan();
		System.out.println(form);
		
		plan.setId(form.getId());
		plan.setName(form.getName());
		plan.setRoomId(form.getRoomId());
		plan.setBreakfast(form.getBreakfast());
		plan.setDinner(form.getDinner());
		plan.setBasicCharge(form.getBasicCharge());
		plan.setAdditionalCharge(form.getAdditionalCharge());
		plan.setComment(form.getComment());
		
		if(form.getNowImage() == 0) {
			plan.setImage((String) session.getAttribute("image"));
		}else {
			
//			�摜�̖��O���擾�E�t�@�C���ɕۑ�
			MultipartFile multiFile = form.getImage();
			String imageName = multiFile.getOriginalFilename();
			
			File filepath = new File("src/main/resources/static/img/plans/"+imageName);
			try {
				byte[] bytes = multiFile.getBytes();
				FileOutputStream stream = new FileOutputStream(filepath.toString());
				stream.write(bytes);
				stream.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			plan.setImage(imageName);
		}
		
		System.out.println(plan);
		manageService.updatePlan(plan);
		
		return"redirect:/manage/toRegisteredPlanList";
	}
	
	@RequestMapping("/toDeletePlan")
	public String toDeletePlan(Integer id, Model model) {
		
		model.addAttribute("plan", manageService.findPlanById(id));
		
		return"/manage/delete_plan";
	}
	
	@RequestMapping("/deletePlan")
	public String deletePlan(Integer planId) {
		manageService.deletePlan(planId);
		
		return"redirect:/manage/toRegisteredPlanList";
	}
	
	
	@RequestMapping("/toReservationList")
	public String toReservationList(Model model) {
		
		//�f�t�H���g�ō����̗\���\��
		Reservation reservation = new Reservation();
		reservation.setCheckinDate(LocalDate.now());
	
		model.addAttribute("reservationList", manageService.find(reservation));
		
		return "/manage/reservation_list";
	}
	
	@RequestMapping("/searchReservation")
	public String searchReservation(SearchReservationForm form, Model model) {
		
		Reservation reservation = new Reservation();
		reservation.setName(form.getName());
		reservation.setKana(form.getKana());
		reservation.setCheckinDate(form.getDate());
		reservation.setPayMethod(form.getPayMethod());
		
		model.addAttribute("reservationList", manageService.find(reservation));
		
		return "/manage/reservation_list";
	}
	
	@RequestMapping("/toReservationDetail")
	public String toReservationDetail(Integer id, Model model) {
		model.addAttribute("reservation", manageService.findReservationById(id));
		
		return "/manage/reservation_detail";
	}
	
	@RequestMapping("/cancelReservation")
	public String cancelReservation(Integer reservationId) {
		manageService.cancelReservation(reservationId);
		
		return"redirect:/manage/toReservationList";
	}
	
	
}
