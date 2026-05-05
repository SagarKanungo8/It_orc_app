package com.ncs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.config.JwtProvider;
import com.ncs.exception.UserException;
import com.ncs.model.User;
import com.ncs.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JwtProvider jwtProvider;
	

	@Override
	public User findUserById(Long userId) throws UserException {
	
		Optional<User> user = userRepo.findById(userId);
		
		if(user.isPresent()) {
			return user.get();
		}
	
		throw new UserException("User Not Found....");
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		
		String email = jwtProvider.getEmailFromToken(jwt);

		User user = userRepo.findByEmail(email);
		
		if(user==null) {
			throw new UserException("Email Address Not Found");
		}
		
		return user;
	}

}
