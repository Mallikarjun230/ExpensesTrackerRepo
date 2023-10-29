package com.jsp.et.service;

import java.time.LocalDate;
import java.util.List;

import com.jsp.et.DTO.ExpensesDTO;
import com.jsp.et.entity.Expensestable;

public interface ExpenseService 
{
	int addExpense(ExpensesDTO dto,int userId);
	
	List<ExpensesDTO> viewExpense(int userId); 
	
	ExpensesDTO findbyId(int id);
	
	ExpensesDTO updateExpense(ExpensesDTO dto,int id);
	
	int deleteExpense(int id);
	
	List<ExpensesDTO> filterBasedOnDateCategoryAmount(ExpensesDTO dto,int userId);
	
	List<ExpensesDTO> filterBasedOnDate(ExpensesDTO dto,int userId);
	
	List<ExpensesDTO> filterBasedOnCategory(ExpensesDTO dto,int userId);
	
	List<ExpensesDTO> filterBasedOnAmount(String  range,int userId);
	
	List<ExpensesDTO> filterExpenseBasedOnDate(LocalDate start,LocalDate end,int userId);
}
