package com.jsp.et.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserTableDTO 
{
	private int id;
	private String fullName;
	private String userName;
	private String email;
	private String mobile;
	private String password;
	private String repassword;
	
	private ImageDTO image;
}
