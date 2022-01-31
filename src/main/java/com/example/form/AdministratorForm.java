package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AdministratorForm {
	
	@NotNull(message="�]�ƈ��ԍ�����͂��Ă�������")
	private Integer employeeId;
	
	@NotBlank(message="�p�X���[�h����͂��Ă�������")
	@Pattern(regexp="^[a-zA-Z0-9]{8,16}$", message="���������͂��Ă�������")
	private String password;
	
	@NotBlank(message="�m�F�p�p�X���[�h����͂��Ă�������")
	private String passwordConfirm;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@Override
	public String toString() {
		return "AdministratorForm [employeeId=" + employeeId + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + "]";
	}

	
	
	
}
