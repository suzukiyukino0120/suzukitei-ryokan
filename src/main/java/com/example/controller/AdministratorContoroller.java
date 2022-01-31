package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Administrator;
import com.example.form.AdminLoginForm;
import com.example.form.AdministratorForm;
import com.example.service.AdministratorService;
import com.example.service.EmployeeService;

@Controller
@RequestMapping("/administrator")
public class AdministratorContoroller {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	public AdministratorForm setUpAdministratorForm() {
		return new AdministratorForm();
	}
	
	@ModelAttribute
	public AdminLoginForm setUpAdminLoginForm() {
		return new AdminLoginForm();
	}
	
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "/administrator/login";
	}
	
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "/administrator/administrator_register";
	}
	
	@RequestMapping("/register")
	public String register(@Validated AdministratorForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			if(!form.getPassword().equals(form.getPasswordConfirm())){
				result.rejectValue("password", "", "�p�X���[�h����v���Ă��܂���");
			}
			if(employeeService.load(form.getEmployeeId()) == null) {
				result.rejectValue("employeeId", "", "���݂��Ȃ��Ј��ԍ��ł�");
			}else if(administratorService.load(form.getEmployeeId()) != null) {
				result.rejectValue("employeeId", "", "���̎Ј��ԍ��Ŋ��ɓo�^����Ă��܂�");
			}
				return toRegister();
		}
		
		if(!form.getPassword().equals(form.getPasswordConfirm())){
			result.rejectValue("password", "", "�p�X���[�h����v���Ă��܂���");
			return toRegister();
		}
		if(employeeService.load(form.getEmployeeId()) == null) {
			result.rejectValue("employeeId", "", "���݂��Ȃ��Ј��ԍ��ł�");
			return toRegister();
		}else if(administratorService.load(form.getEmployeeId()) != null) {
			result.rejectValue("employeeId", "", "���̎Ј��ԍ��Ŋ��ɓo�^����Ă��܂�");
			return toRegister();
		}
		
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		
		administratorService.insert(administrator);
		
		return "/administrator/login";
	}
	
	@RequestMapping("/login")
	public String login(@Validated AdminLoginForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "/administrator/login";
		}
		
		Administrator administrator = administratorService.findByIdAndPass(form.getEmployeeId(), form.getPassword());
			
		if(administrator == null) {
				model.addAttribute("loginError", "�Ј��ԍ����p�X���[�h���Ԉ���Ă��܂�");
			
				return "/administrator/login";
			}else {
					session.setAttribute("administrator", administrator);
		
					return "/administrator/reservation_list";
			}
	}
	
	
	
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();		
		return "/administrator/login";
	}
	
	
}
