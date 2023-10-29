package com.jsp.et.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.et.DTO.ImageDTO;
import com.jsp.et.DTO.UserTableDTO;
import com.jsp.et.entity.Image;
import com.jsp.et.entity.UserTable;
import com.jsp.et.repository.UserTableRepository;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserTableRepository userRepo;
	
	//This method is to register the new user 
	@Override
	public int registratrion(UserTableDTO dto)
	{
		//create object of entity class
		UserTable user = new UserTable();
		/*
		 * transfer data from dto to entity object
		 * basic way
		 * user.setUserName(dto.getUserName);
		 * user.setFullName(dto.getFullName);
		 * 
		 */
		if(dto.getPassword().equals(dto.getRepassword()))
		{
			BeanUtils.copyProperties(dto, user);
			return userRepo.save(user).getId();
		}
		return 0;	
	}
	
	//It is to fetch the user by taking id
	@Override
	public UserTableDTO findById(int userid) {
		UserTableDTO dto =new UserTableDTO();
		BeanUtils.copyProperties(userRepo.findById(userid).get(), dto);
		return null;
	}
	
	// It is used to make changes to the user details
	@Override
	public UserTableDTO updateUserProfile(UserTableDTO dto, int userid) {
		UserTable user = userRepo.findById(userid).get();
		user.setEmail(dto.getEmail());
		user.setFullName(dto.getFullName());
		user.setMobile(dto.getMobile());
		user.setPassword(dto.getPassword());
		user.setUserName(dto.getUserName());
		
		// to add image Object into user object
		if(dto.getImage().getData().length != 0)
		{
			
			Image img = new Image();
			BeanUtils.copyProperties(dto.getImage(), img);
			user.setImage(img);
		}
		
		UserTableDTO updated = new UserTableDTO();
		BeanUtils.copyProperties(userRepo.save(user), updated);
		return updated;
	}
	
	// It is used to delete the user from the database
	@Override
	public int deleteUser(int userid) {
		Optional<UserTable> user = userRepo.findById(userid);
		if (user.isPresent())
		{
			userRepo.delete(user.get());
		}
		return 0;
	}
	@Override
	public UserTableDTO login(UserTableDTO dto) 
	{
		UserTableDTO verified = new UserTableDTO();
		Optional<UserTable> findByUserNameandPassword = userRepo.findByUserNameAndPassword(dto.getUserName(), dto.getPassword());
		if(findByUserNameandPassword.isPresent())
		{
			UserTable userdb = findByUserNameandPassword.get();
			BeanUtils.copyProperties(userdb, verified);
			
			//to store ImageDTO object into userDTO
			if(userdb.getImage() != null)
			{
				ImageDTO imageDTO = new ImageDTO();
				BeanUtils.copyProperties(userdb.getImage(), imageDTO);
				verified.setImage(imageDTO);
			}
			return verified;
		}
		return null;
	}
	@Override
	public UserTableDTO forgotPassword(UserTableDTO dto ,String mail,String password)
	{
		UserTable user = userRepo.findByEmail(mail);
		dto.setPassword(password);
		if(user!=null)
		{
			user.setPassword(dto.getPassword());
		
			UserTableDTO updated = new UserTableDTO();
			BeanUtils.copyProperties(userRepo.save(user),updated);
			return updated;
		}
		return null;
	}
}
