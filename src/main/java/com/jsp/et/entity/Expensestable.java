package com.jsp.et.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Expensestable 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false,unique = false)
	private LocalDate date;
	@Column(nullable = false,unique = false)
	private double amount;
	@Column(nullable = false,unique = false)
	private String description;
	
	@ManyToOne
	private ExpenseCategory category;
	
	@ManyToOne
	private UserTable user;
}
