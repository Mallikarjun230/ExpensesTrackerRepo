package com.jsp.et.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.et.entity.ExpenseCategory;
import com.jsp.et.entity.Expensestable;
import com.jsp.et.entity.UserTable;

public interface ExpensesRepository extends JpaRepository<Expensestable, Integer>
{
	List<Expensestable> findByUser(UserTable user);

}
