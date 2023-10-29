	package com.jsp.et.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsp.et.DTO.ExpensesDTO;
import com.jsp.et.service.ExpenseService;

@Controller

public class AppController 
{
   @RequestMapping("/home")
   public String home()
   {
	   
	   return "index";
   }
   
   
   @RequestMapping("/login")
   public String login(@ModelAttribute("msg") String message)
   {
	   return "Login";
   }
   @RequestMapping("/signin")
   public String signin(@ModelAttribute("msg") String message)
   {
	   return "Signin";
   }
   @RequestMapping("/welcome")
   public String welcome()
   {
	 return "Welcome";  
   }
   
   @RequestMapping("/viewexpense")
   public String viewExpenses(@ModelAttribute("listOfExpenses") List<ExpensesDTO> expenses)
   {
	   return "ViewExpense";
	   
   }
   @RequestMapping("/addexpense")
   public String addExpenses(@ModelAttribute("error") String message)
   {
	   return "AddExpenses";
	   
   }
   @RequestMapping("/totalexpense")
   public String totalExpenses()
   {
	   return "TotalExpenses";
	   
   }
   @RequestMapping("/filterexpense")
   public String filterExpenses()
   {
	   return "FilterExpenses";
	   
   }
   
   @Autowired
   private ExpenseService service ;
   
   @RequestMapping("/update/{eid}")
   public String updateExpenses(@PathVariable("eid") int id ,Model m)
   {
	   ExpensesDTO dto = service.findbyId(id);
	   m.addAttribute("expense",dto);
	   return "UpdateExpense";
	   
   }
   @RequestMapping("/updateprofile")
   public String updateProfile()
   {
	   return "UpdateProfile";
   }
   
   @RequestMapping("/forgotpassword")
   public String forgotPassword(@ModelAttribute("msg") String message)
   {
	   return "ForgotPassword";
   }
   
	
}