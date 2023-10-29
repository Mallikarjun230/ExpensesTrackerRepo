package com.jsp.et.controller;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.et.DTO.ExpensesDTO;
import com.jsp.et.DTO.ImageDTO;
import com.jsp.et.DTO.TotalDTO;
import com.jsp.et.DTO.UserTableDTO;
import com.jsp.et.entity.Expensestable;
import com.jsp.et.entity.UserTable;
import com.jsp.et.service.ExpenseService;
import com.jsp.et.service.ExpenseServiceImpl;
import com.jsp.et.service.UserService;
@Controller
public class UserController 
{
	@Autowired
	private UserService service;
	
	@Autowired
	private ExpenseService expenseService;
	
	@GetMapping("/getusetobject")
	public UserTableDTO getUserObject()
	{
		UserTableDTO user = new UserTableDTO();
		return user;
	}
	@PostMapping("/register")
	public String registration(UserTableDTO user,Model m,RedirectAttributes attributes)
	{
		int result = service.registratrion(user);
		if(result!=0)
		{
			//display login page
			//redirect request to login method
			m.addAttribute("msg","Registration Successful");
			attributes.addFlashAttribute("msg","Registration Successful");
			return "redirect:/login";
		}
		m.addAttribute("msg","Enter Valid details");
		attributes.addFlashAttribute("msg","Enter Valid details");
		return "redirect:/signin";
			
	}
	
	@PostMapping("/loginOperation")
	public String login(@ModelAttribute UserTableDTO userdto,RedirectAttributes attributes,HttpServletRequest request)
	{
		UserTableDTO dto = service.login(userdto);
		if(dto!=null)
		{
			// to store users data in session object
			request.getSession().setAttribute("User", dto);
			if(dto.getImage()!=null)
			{
				/*
				 * store image in session object but in the form of String 
				 * by using base64 class present in java.util.package - programmer can encode
				 * byte data to string 
				 */
				request.getSession().setAttribute("image",Base64.getMimeEncoder().encodeToString(dto.getImage().getData()));
			}
			return "redirect:/welcome";
							
		}
		attributes.addFlashAttribute("msg","Please Enter Valid Credentials..");
		return "redirect:/login";
	}
	
	@PostMapping("/getObject")
	public ExpensesDTO getExpenseObject()
	{
		ExpensesDTO dto = new ExpensesDTO();
		return dto;
	}
	@PostMapping("/addExpense/{id}")
	public String addExpense(@ModelAttribute ExpensesDTO dto,@PathVariable("id") int userId,RedirectAttributes attributes)
	{
		int id = expenseService.addExpense(dto,userId);
		System.out.println(userId);
		if(id > 0)
		{
			
			return "redirect:/viewExpense/"+userId;
		}
		attributes.addFlashAttribute("error","Please Enter Valid Details");
		return  "redirect:/addexpense";
	}
	
	@GetMapping("/viewExpense/{id}")
	public String  viewExpenses(@PathVariable("id") int userId,RedirectAttributes attributes)
	{
		List<ExpensesDTO> expenses = expenseService.viewExpense(userId);
		if (!expenses.isEmpty())
		{
			 attributes.addFlashAttribute("listOfExpenses",expenses);
			 return "redirect:/viewexpense";
		}
		return "redirect:/welcome";
		
	}
	@PostMapping("/updateExpense/{eid}")
	public String updateExpense(@ModelAttribute ExpensesDTO dto,@PathVariable("eid") int id,HttpServletRequest request)
	{
		ExpensesDTO expenses = expenseService.updateExpense(dto, id);
		if(expenses!=null)
		{
			UserTableDTO userdto =  (UserTableDTO)request.getSession().getAttribute("User");
			return "redirect:/viewExpense/"+userdto.getId();
		}
		return "redirect:/welcome";
	}
	
	@GetMapping("/deleteexpense/{eid}")
	public String deleteExpense(@PathVariable("eid") int expenseId,HttpServletRequest request)
	{
		int res =  expenseService.deleteExpense(expenseId);
		if(res!=0)
		{
			UserTableDTO dto = (UserTableDTO)request.getSession().getAttribute("User");
			return "redirect:/viewExpense/"+dto.getId();
		}
		return "redirect:/welcome";
	}
	
	@GetMapping("/findExpense/{id}")
	public ResponseEntity<ExpensesDTO> findByExpenseId(@PathVariable("id") int id)
	{
		ExpensesDTO dto = expenseService.findbyId(id);
		if(dto!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}	
	@GetMapping("/filter/{userid}")
	public String filterBasedOnDateCategoryAmount(@ModelAttribute ExpensesDTO dto,@PathVariable("userid") int userId,RedirectAttributes attributes)
	{
		
		if(!dto.getRange().equalsIgnoreCase("0"))
		{
			List<ExpensesDTO> filterByAmount = expenseService.filterBasedOnAmount(dto.getRange(),userId);
			attributes.addFlashAttribute("listOfExpenses",filterByAmount);
			return "redirect:/viewexpense";
		}
		else if(dto.getCategory() != "")
		{
			List<ExpensesDTO> filterByCategory = expenseService.filterBasedOnCategory(dto,userId);
			attributes.addFlashAttribute("listOfExpenses",filterByCategory);
			return "redirect:/viewexpense";
		}
		else if(dto.getDate() != "")
		{
			List<ExpensesDTO> filterByDate = expenseService.filterBasedOnDate(dto,userId);
			attributes.addFlashAttribute("listOfExpenses",filterByDate);
			return "redirect:/viewexpense";
		}
		return "redirect:/welcome";
	}
	
	@GetMapping("/filter/expense2/{userid}")
	public ResponseEntity<List<ExpensesDTO>> filterBasedOnDate(@RequestBody ExpensesDTO dto,@PathVariable("userid") int userid)
	{
		List<ExpensesDTO> expenses = expenseService.filterBasedOnDate(dto, userid);
		if(expenses!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(expenses);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping("/filter/expense3/{userid}")
	public ResponseEntity<List<ExpensesDTO>> filterBasedOnCategory(@RequestBody ExpensesDTO dto,@PathVariable("userid") int userId)
	{
		List<ExpensesDTO> expenses = expenseService.filterBasedOnCategory(dto, userId);
		if(expenses!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(expenses);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@GetMapping("/filter/expense4/range/{userid}")
	public ResponseEntity<List<ExpensesDTO>> filterBasedOnAmount(@PathVariable("range") String range,@PathVariable("userid") int userId)
	{

		List<ExpensesDTO> expenses = expenseService.filterBasedOnAmount(range, userId);
		if(expenses!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(expenses);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	@GetMapping("/total/{userid}")
	public String getTotalExpenseBasedOnDate(@ModelAttribute TotalDTO total, @PathVariable("userid") int userId,Model m) 
	{
		List<ExpensesDTO> dto =  expenseService.filterExpenseBasedOnDate(LocalDate.parse(total.getStart()),
								 LocalDate.parse(total.getEnd()), userId);
		
		m.addAttribute("listOfExpenses", dto);
		m.addAttribute("total",dto.stream().mapToDouble(t -> t.getAmount()).sum());
		
		return "viewExpense";
	}
	@GetMapping("/userbyid/{id}")
	public ResponseEntity<UserTableDTO> getUserById(@PathVariable("id") int userId)
	{
		UserTableDTO user = service.findById(userId);
		if (user!=null)
		{
			return  ResponseEntity.status(HttpStatus.OK).body(user);
		}
		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	@PostMapping("/updateuser/{id}")
	public String updateUser(@ModelAttribute UserTableDTO dto,@PathVariable("id") int userid,@RequestParam("imageFile") MultipartFile file,RedirectAttributes attributes,HttpServletRequest request)
	{
		try
		{
			/**
			 * retrieve UserDTO object from session object ,store at the time of Login
			 */
			UserTableDTO fromSession = (UserTableDTO)request.getSession().getAttribute("User");
			
			//if user already have uploaded profile photo then update the photo
			if(fromSession.getImage()!=null)
			{
				// Updation logic
				fromSession.getImage().setData(file.getBytes());
				dto.setImage(fromSession.getImage());
				
				// to store same in session object 
				request.getSession().setAttribute("image", Base64.getMimeEncoder().encodeToString(dto.getImage().getData()));
			}
			else
			{
				// if user uploading profile photo for the first time
				ImageDTO imagedto = new ImageDTO();
				imagedto.setData(file.getBytes());
				dto.setImage(imagedto);
			}
			
			service.updateUserProfile(dto,userid);
			attributes.addFlashAttribute("msg","Profile updated");
			return "redirect:/login";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "redirect/welcome";
	}
	
	@GetMapping("/deleteuser/{id}")
	public String deleteUser(@PathVariable("id") int userid,RedirectAttributes attributes)
	{
			int result = service.deleteUser(userid);
			
			if(result!=0)
			{
				
				attributes.addFlashAttribute("msg","your account has been deleted as per your wish");
				return "redirect:/login";
			}
			attributes.addFlashAttribute("msg","Account Cannot be deleted");
			return "redirect:/updateprofile";
	}
	
	@GetMapping("/forgotpass")
	public String forgotPassword(@ModelAttribute UserTableDTO dto,String  email,RedirectAttributes attributes,String password)
	{
		UserTableDTO userdto = service.forgotPassword(dto,email,password);
		if(userdto!=null)
		{
			
			attributes.addFlashAttribute("msg","Password Updated");
			return "redirect:/forgotpassword";
		}
		attributes.addFlashAttribute("msg","Enter Valid Email..!!");
		return "redirect:/forgotpassword";
	}
}



















