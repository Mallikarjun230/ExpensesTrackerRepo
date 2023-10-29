package com.jsp.et.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.et.entity.UserTable;

public interface UserTableRepository  extends JpaRepository<UserTable, Integer>
{
	Optional<UserTable> findByUserNameAndPassword(String username,String password);
	
	UserTable  findByEmail(String mail);
	
	
}
