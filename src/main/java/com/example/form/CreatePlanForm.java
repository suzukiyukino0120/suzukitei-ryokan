package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;


public class CreatePlanForm {
	
	@NotBlank(message="�v����������͂��Ă�������")
	private String name;

	@NotNull(message="�����^�C�v��I�����Ă�������")
	private Integer roomId;
	
	@NotNull(message="���H�̗L����I�����Ă�������")
	private Integer breakfast;
	
	@NotNull(message="�[�H�̗L����I�����Ă�������")
	private Integer dinner;
	
	@NotNull(message="��{��������͂��Ă�������")
	private Integer basicCharge;
	
	@NotNull(message="�ǉ���������͂��Ă�������")
	private Integer additionalCharge;
	
	@NotBlank(message="�v������������͂��Ă�������")
	private String comment;
	
	@NotBlank(message="�v�����̃C���[�W�摜��Y�t���Ă�������")
	private MultipartFile image;
	



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(Integer breakfast) {
		this.breakfast = breakfast;
	}

	public Integer getDinner() {
		return dinner;
	}

	public void setDinner(Integer dinner) {
		this.dinner = dinner;
	}

	public Integer getBasicCharge() {
		return basicCharge;
	}

	public void setBasicCharge(Integer basicCharge) {
		this.basicCharge = basicCharge;
	}

	public Integer getAdditionalCharge() {
		return additionalCharge;
	}

	public void setAdditionalCharge(Integer additionalCharge) {
		this.additionalCharge = additionalCharge;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "CreatePlanForm [name=" + name + ", roomId=" + roomId + ", breakfast=" + breakfast + ", dinner=" + dinner
				+ ", basicCharge=" + basicCharge + ", additionalCharge=" + additionalCharge + ", comment=" + comment
				+ ", image=" + image + "]";
	}


	
	
	

}