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
				result.rejectValue("password", "", "パスワードが一致していません");
			}
			if(employeeService.load(form.getEmployeeId()) == null) {
				result.rejectValue("employeeId", "", "存在しない社員番号です");
			}else if(administratorService.load(form.getEmployeeId()) != null) {
				result.rejectValue("employeeId", "", "この社員番号で既に登録されています");
			}
				return toRegister();
		}
		
		if(!form.getPassword().equals(form.getPasswordConfirm())){
			result.rejectValue("password", "", "パスワードが一致していません");
			return toRegister();
		}
		if(employeeService.load(form.getEmployeeId()) == null) {
			result.rejectValue("employeeId", "", "存在しない社員番号です");
			return toRegister();
		}else if(administratorService.load(form.getEmployeeId()) != null) {
			result.rejectValue("employeeId", "", "この社員番号で既に登録されています");
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
				model.addAttribute("loginError", "社員番号かパスワードが間違っています");
			
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
