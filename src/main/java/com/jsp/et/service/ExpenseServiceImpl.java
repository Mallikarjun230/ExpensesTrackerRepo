package com.jsp.et.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.et.DTO.ExpensesDTO;
import com.jsp.et.entity.ExpenseCategory;
import com.jsp.et.entity.Expensestable;
import com.jsp.et.entity.UserTable;
import com.jsp.et.repository.ExpenseCategoryRepository;
import com.jsp.et.repository.ExpensesRepository;
import com.jsp.et.repository.UserTableRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService 
{
	@Autowired
	private ExpensesRepository expenseRepository;
	
	@Autowired
	private ExpenseCategoryRepository categoryRepository;
	
	@Autowired
	private UserTableRepository userRepository;
	@Override
	
	/*		
	 * 	Expense table contains two foreign keys -categoryId and UserId
	 * to insert value in foreign key columns ,firstly programmer have to verify users category 
	 * then need to retrieve it by using name and id 
	 * after that insert into Expense table
	 */
	public int addExpense(ExpensesDTO dto,int userId) 
	{
		Optional<ExpenseCategory> category = categoryRepository.findByCategory(dto.getCategory());
		Optional<UserTable> user = userRepository.findById(userId);
		if(category.isPresent() && user.isPresent())
		{
			Expensestable expense = new Expensestable();
			// convert String dto date to LocalDate of Entity
			expense.setDate(LocalDate.parse(dto.getDate()));
			BeanUtils.copyProperties(dto, expense);
			expense.setCategory(category.get());
			expense.setUser(user.get());
			
			return expenseRepository.save(expense).getId();
		}
		return 0;
	}
	@Override
	public List<ExpensesDTO> viewExpense(int userId)
	{
		
		UserTable user = userRepository.findById(userId).get();
		return expenseRepository.findByUser(user).stream().map(t->{
			ExpensesDTO dto = new ExpensesDTO();
			BeanUtils.copyProperties(t, dto);
			dto.setCategory(t.getCategory().getCategory());
			dto.setDate(t.getDate().toString());
			return dto;
		}).collect(Collectors.toList());	
	}
	@Override
	public ExpensesDTO findbyId(int id)
	{
		Optional<Expensestable> expensedb = expenseRepository.findById(id);
		if(expensedb.isPresent())
		{
			ExpensesDTO dto = new ExpensesDTO();
			BeanUtils.copyProperties(expensedb.get(), dto);
			dto.setCategory(expensedb.get().getCategory().getCategory());
			dto.setDate(expensedb.get().getDate().toString());
			return dto;
		}
		return null;
	}
	@Override
	public ExpensesDTO updateExpense(ExpensesDTO dto, int id)
	{
		Expensestable expense = expenseRepository.findById(id).get();
		if (expense!=null)
		{
			expense.setAmount(dto.getAmount());
			expense.setDate(LocalDate.parse(dto.getDate()));
			expense.setDescription(dto.getDescription());
		}
		ExpenseCategory category = categoryRepository.findByCategory(dto.getCategory()).get();
		expense.setCategory(category);
		ExpensesDTO updated = new ExpensesDTO();
		BeanUtils.copyProperties(expenseRepository.save(expense), updated);
		return updated;
	}
	@Override
	public int deleteExpense(int id) 
	{
		Optional<Expensestable> expenseDB = expenseRepository.findById(id);
		if (expenseDB.isPresent())
		{
			expenseRepository.delete(expenseDB.get());
			return 1;
		}
		return 0;
	}
	@Override
	public List<ExpensesDTO> filterBasedOnDateCategoryAmount(ExpensesDTO dto, int userId) {
		
		return viewExpense(userId).stream()
				.filter(t->t.getDate().equals(dto.getDate()) && t.getAmount() == dto.getAmount() 
				&& t.getCategory().equals(dto.getCategory())).toList();
	}
	/*
	 * It will retrieve data from db based on matching date make use of filter method from 
	 * Stream api
	 * 
	 */
	@Override
	public List<ExpensesDTO> filterBasedOnDate(ExpensesDTO dto, int userId) {
		
		return viewExpense(userId).stream()
				.filter(t->t.getDate().equals(dto.getDate())).toList();
	}
	
	/*
	 * It will retrieve data from db based on matching category make use of filter method
	 * 	from stream api 
	 */
	@Override
	public List<ExpensesDTO> filterBasedOnCategory(ExpensesDTO dto, int userId) {
		
		return viewExpense(userId).stream()
				.filter(t->t.getCategory().equals(dto.getCategory())).toList();
	}
	
	/*
	 * It will retrieve data from db based on matching range of amount use of filter 
	 * method  from stream api
	 * Take range in format of String "100-200"
	 */
	@Override
	public List<ExpensesDTO> filterBasedOnAmount(String range, int userId)
	{
		String[] arr = range.split("-");
		return viewExpense(userId).stream().filter(t->{
			int start =Integer.parseInt(arr[0]);
			int end =Integer.parseInt(arr[1]);
			return start <= t.getAmount() && end >=t.getAmount();
		}).collect(Collectors.toList());
	}
	//It will find total of expenses between given date for respective user */
	 @Override
	public List<ExpensesDTO> filterExpenseBasedOnDate(LocalDate start,LocalDate end,int userId)
	 {
		 /* 1.get all expenses for respective user 
		  * 2.make use of streams API to filter expenses based on given start and end date 
		  */
		 return viewExpense(userId).stream().filter(t ->{ 
				LocalDate date = LocalDate.parse(t.getDate());
				return !date.isBefore(start) && !date.isAfter(end);
			}).collect(Collectors.toList());
	}
}






























