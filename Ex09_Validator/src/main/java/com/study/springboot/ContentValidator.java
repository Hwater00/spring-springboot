package com.study.springboot;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContentValidator implements Validator {
	
	public boolean supports(Class<?> arg0) {
		return ContentDto.class.isAssignableFrom(arg0); 
	}
	
	// 자식은 부모에게 대입이 가능하기 때문에 자식타입으로  dto를 선언하고 매개변수로 들어오는 값은 obj 자식이면 다 가능함.
	public void validate(Object obj, Errors errors) {
		ContentDto dto = (ContentDto)obj; // 새로 불러온 값을 강제 타입변환으로 ContentDto로 변환. => // 검사하고자 하는 필드값을 가져올 수 있음
		
		// 쓰는 값에 대한 검사
		String sWriter = dto.getWriter();
		if(sWriter == null || sWriter.trim().isEmpty()) { 
			System.out.println("Writer is null or empty");
			errors.rejectValue("writer","trouble");
		}
		
		// 얻어온 값에 대한 검사
		String sContent = dto.getContent();
		if(sContent == null || sContent.trim().isEmpty()) {
			System.out.println("Content is null or empty");
			errors.rejectValue("content","trouble");
		}
		
	}
	
	
}
