package com.example.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.Validator;

import com.example.form.UpdatePlanForm;

@Component
public class UpdatePlanValidator implements Validator{
	
	@Override
    public boolean supports(Class<?> clazz) {

		return UpdatePlanForm.class.isAssignableFrom(clazz);
    }
	
	@Override
	    public void validate(Object form, Errors errors) {
	    	UpdatePlanForm validationForm = (UpdatePlanForm)form;
	        Integer nowImage = validationForm.getNowImage();
	        MultipartFile image = validationForm.getImage();
	        
	        if(nowImage == 1) {
				if(image.isEmpty()) {
					errors.rejectValue("image","", "�C���[�W��ύX����ꍇ�̓C���[�W��Y�t���Ă��������B");
				}
			}else {
				if(!image.isEmpty()) {
					errors.rejectValue("image","", "�C���[�W��ύX���Ȃ��ꍇ�̓C���[�W��Y�t���Ȃ��ł�������");
				}
			}
	    }




}
