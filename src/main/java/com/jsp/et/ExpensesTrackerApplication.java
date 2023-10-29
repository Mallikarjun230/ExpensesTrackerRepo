package com.jsp.et;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jsp.et.entity.ExpenseCategory;
import com.jsp.et.repository.ExpenseCategoryRepository;

@SpringBootApplication
public class ExpensesTrackerApplication implements CommandLineRunner
{
	@Autowired
	private ExpenseCategoryRepository repo;
	
	public static void main(String[] args)
	{
		SpringApplication.run(ExpensesTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		List<ExpenseCategory> category=Arrays.asList(
				new ExpenseCategory("Utilities"),
				new ExpenseCategory("Transportation"),
				new ExpenseCategory("Groceries"),
				new ExpenseCategory("Dining out"),
				new ExpenseCategory("Health Care"),
				new ExpenseCategory("Entertainment"),
				new ExpenseCategory("Personal Care"),
				new ExpenseCategory("Education"),
				new ExpenseCategory("Clothing"),
				new ExpenseCategory("Home Maintenance"),
				new ExpenseCategory("Gifts & Donations"),
				new ExpenseCategory("Savings and Investment"),
				new ExpenseCategory("Tax"),
				new ExpenseCategory("Others"));
				//repo.saveAll(category);
		
	}

}
