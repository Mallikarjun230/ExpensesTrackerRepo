package com.jsp.et.DTO;

import java.time.LocalDate;

import javax.persistence.Column;

import com.jsp.et.entity.ExpenseCategory;
import com.jsp.et.entity.UserTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExpensesDTO 
{
	
	private int id;
	private String date;
	private double amount;
	private String description;
	//to take range of amount selected by user
	private String range;
	//creating Expense categoryDto rv may create difficulties in service layer with String
	private String category;

}
