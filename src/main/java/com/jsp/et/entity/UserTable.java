package com.jsp.et.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class UserTable 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String fullName;
	private String userName;
	private String email;
	private String mobile;
	private String password;
	
	@OneToMany(mappedBy = "user")
	private List<Expensestable> expenses;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="image_id")
	private Image image;
}
