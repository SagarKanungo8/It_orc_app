package com.ncs.service;

import com.ncs.exception.UserException;
import com.ncs.model.User;

public interface UserService {
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	
	
	
	
	  
	
	

}
