package com.jsp.et.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.et.entity.UserTable;

@RestController
public class SampleController 
{
	@RequestMapping("/msg")
	public String message()
	{
		return "Rest Controller annotations";
	}
	
	
	@RequestMapping("/index")
	public ModelAndView index()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
	
	@GetMapping("/input/{number}")
	public String getInput(@PathVariable("number") int  num)
	{
		return "Input = " + num;
	}
	
	
	@PostMapping("/add/{number1}/{number2}")
	public String sum(@PathVariable("number1")int n1,@PathVariable("number2")int n2)
	{
		return "Addition = " + (n1 + n2);
	}
	
	@GetMapping("user")
	public UserTable getUser()
	{
		UserTable user = new UserTable();
		return user;
	}
	
	@GetMapping("/name")
	public String userName(@RequestBody UserTable user)
	{
		return user.getFullName();
	}
}








