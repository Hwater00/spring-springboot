package com.study.springboot;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ContentValidator implements Validator {
	
// supports
	public boolean supports(Class<?> arg0) {
		return ContentDto.class.isAssignableFrom(arg0); 	//검증할 객테의 클래스 타입 정보
	}
	
	// 자식은 부모에게 대입이 가능하기 때문에 자식타입으로  dto를 선언하고 매개변수로 들어오는 값은 obj 자식이면 다 가능함.
	public void validate(Object obj, Errors errors) {
		ContentDto dto = (ContentDto)obj;
		
		//org.springframework.validation.ValidationUtils= Utils에 의해 코드 간결화
		
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
