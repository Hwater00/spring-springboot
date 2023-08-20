package com.study.springboot;


import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MyController {
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception{
		return "From 데이터 전달받아 사용하기";
	}
	
	//http://localhost:8081/test숫자?id=hong&name=
	@RequestMapping("/test1") //서블렛 방식
	public String test1(HttpServletRequest httpServletRequest, Model model) {
		String id = httpServletRequest.getParameter("id");
		String name= httpServletRequest.getParameter("name");
		
		model.addAttribute("id",id);
		model.addAttribute("name",name);
		
		
		return "test1";
	}
	
	@RequestMapping("/test2") //매개변수로 필요한 파라미터값만 가져옴
	public String test2(@RequestParam("id") String id, @RequestParam("name") String name,
			Model model)
	{
		model.addAttribute("id",id);
		model.addAttribute("name",name);
		
		return "test2";
	}
	
	@RequestMapping("/test3") // 파라미터와 이름이 가은 변수를 가진 커멘드 객체를 이용하면 쉽고 간단하게 많은 데이터를 받아서 처리할 수 있다.
	public String test3(Member member,Model model) {
		return "test3";
	}
	
	//http://localhost:8081/test4/hong
	@RequestMapping("/test4/{studentId}/{name}") //{}변수명으로 넣어 사용
	public String getSudent(@PathVariable String studentId,
			@PathVariable String name,
			Model model)
	{
		model.addAttribute("id",studentId);
		model.addAttribute("name",name);
		return "test4"; 
	}
	
}
