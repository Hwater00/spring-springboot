package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception{
		return "Validator(3)";
	}
	
	@RequestMapping("/insertForm")
	public String insert1(){
		return "createPage";
	}
	@RequestMapping("/create")
	public String insert2(@ModelAttribute("dto") ContentDto contentDto,
			BindingResult result)
	{
		String page = "createDonePage";
		System.out.println(contentDto);
		
		ContentValidator validator = new ContentValidator();
		
		//
		validator.validate(contentDto,result);
		
		if(result.hasErrors()) {
			System.out.println("getAllErrors : " + result.getAllErrors());
			
			// null이 어떤 입력에서 발생하는지 발생하는 코드
			if(result.getFieldError("writer") !=null) {
				System.out.println("1:"+result.getFieldError("writer").getCode());
			}
			if(result.getFieldError("content")!= null) {
				System.out.println("2:" + result.getFieldError("content").getCode());
			}
			
			
//			어노테이션 사용으로 최종버전
//			if(result.getFieldError("writer") !=null) {
//				System.out.println("1:"+result.getFieldError("writer").getDefaultMessage());
//			}
//			if(result.getFieldError("content")!= null) {
//				System.out.println("2:" + result.getFieldError("content").getDefaultMessage());
//			}
			page = "createPage";
		}
		return page;

			
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) { //프로젝트가 시작할 때 init으로 관리,
		binder.setValidator(new ContentValidator()); // 시작 시 유효성 검사를 한다. 
	}
	
}
