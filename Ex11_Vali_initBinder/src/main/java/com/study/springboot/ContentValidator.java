package com.study.springboot;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ContentValidator implements Validator {
	
// supports
	public boolean supports(Class<?> arg0) {
		return ContentDto.class.isAssignableFrom(arg0); 
	}
	
	
	public void validate(Object obj, Errors errors) {
		ContentDto dto = (ContentDto)obj;
		
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "writer", "writer is empty");
		  String sWriter = dto.getWriter();
		//System.out.println(sWriter);
		//if (sWriter != null &&sWriter.length()<3) {
		  
		if (sWriter.length()<3) {
			errors.rejectValue( "writer", "writer is too sort");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "content is empty");
		
	}
	
	
}