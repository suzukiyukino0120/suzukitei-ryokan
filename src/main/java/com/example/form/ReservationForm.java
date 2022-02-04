package com.example.form;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservationForm {
	
	@NotBlank(message="���O����͂��ĉ�����")
	private String name;
	
	@NotBlank(message="�ӂ肪�Ȃ���͂��ĉ�����")
	@Pattern(regexp="^[��-��[]*$", message="�Ђ炪�Ȃ���͂��Ă�������")
	private String kana;
	
	@NotBlank(message="�X�֔ԍ�����͂��Ă�������")
	@Pattern(regexp="^[0-9]{7}$", message="�X�֔ԍ��̓n�C�t���Ȃ��̂V���œ��͂��Ă�������")
	private String zipcode;
	
	@NotBlank(message="�Z������͂��ĉ�����")
	private String address;
	
	@NotBlank(message="�d�b�ԍ�����͂��Ă�������")
	@Pattern(regexp="^[0-9]{9,12}$", message="�d�b�ԍ��̓n�C�t���Ȃ���9���`12���œ��͂��Ă�������")
	private String telephone;
	
	@NotBlank(message="���[���A�h���X����͂��ĉ�����")
	@Email(message="���[���A�h���X�̌`�����s���ł�")
	private String email;
	
	@NotNull(message="�`�F�b�N�C���̓��t����͂��Ă�������")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkinDate;
	
	@NotNull(message="�h����������͂��Ă�������")
	private Integer stayDays;
	
	@NotNull(message="�h���l������͂��Ă�������")
	private Integer numOfGuest;
	
	@NotNull(message="���x�����@��I�����Ă�������")
	private Integer payMethod;
	
	private Integer planId;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKana() {
		return kana;
	}

	public void setKana(String kana) {
		this.kana = kana;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Integer getStayDays() {
		return stayDays;
	}

	public void setStayDays(Integer stayDays) {
		this.stayDays = stayDays;
	}

	public Integer getNumOfGuest() {
		return numOfGuest;
	}

	public void setNumOfGuest(Integer numOfGuest) {
		this.numOfGuest = numOfGuest;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	
	

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	@Override
	public String toString() {
		return "ReservationForm [name=" + name + ", kana=" + kana + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", email=" + email + ", checkinDate=" + checkinDate + ", stayDays="
				+ stayDays + ", numOfGuest=" + numOfGuest + ", payMethod=" + payMethod + ", planId=" + planId + "]";
	}



	

}
