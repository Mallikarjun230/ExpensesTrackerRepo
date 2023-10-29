package com.jsp.et.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class ExpenseCategory 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(unique = true,nullable = false)
	private String category;
	
	public ExpenseCategory(String category)
	{
		this.category = category;
	}
	
	@OneToMany(mappedBy = "category")
	private List<Expensestable> expenses;

	
	
	
}
