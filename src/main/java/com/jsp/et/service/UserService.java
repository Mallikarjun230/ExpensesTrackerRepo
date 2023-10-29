package com.jsp.et.service;

import com.jsp.et.DTO.UserTableDTO;

public interface UserService 
{
	int registratrion(UserTableDTO dto);
	
	UserTableDTO findById(int userid);
	
	UserTableDTO updateUserProfile(UserTableDTO dto,int userid);
	
	int deleteUser(int userid);
	
	UserTableDTO login(UserTableDTO dto);
	
	UserTableDTO forgotPassword(UserTableDTO dto ,String email,String password);
	
}
