package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception{
		return "Validator(1)";
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
		
		if(result.hasErrors()) { //hasErrors()로 에러가 있는지 확인.
			page= "createPage";
		}
		return page;
	}
	
	
}
