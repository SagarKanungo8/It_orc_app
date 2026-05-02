package com.ncs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncs.config.JwtProvider;
import com.ncs.exception.UserException;
import com.ncs.model.User;
import com.ncs.repository.UserRepo;
import com.ncs.request.LoginRequest;
import com.ncs.response.AuthResponse;
import com.ncs.service.CustomUserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomUserServiceImpl customUserServiceImpl;
	

	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
		
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		User isEmailExist = userRepo.findByEmail(email);
		
		if(isEmailExist!=null) {
			throw new UserException("Email Id Already Exist....!!");
		}
		
		User createdUser = new User();
		
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		
		User savedUser = userRepo.save(createdUser);
		
		Authentication authentication =  new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		
		AuthResponse authResponse = new AuthResponse(token,"SignUp Success...");
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest){
		
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authentication(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		AuthResponse res = new AuthResponse();
		
		res.setMessage("login Success...");
		res.setJwt(token);

		return new ResponseEntity<AuthResponse>(res,HttpStatus.OK);
	}
	

	
	public Authentication authentication(String username,String password) {
		 UserDetails userDetails = customUserServiceImpl.loadUserByUsername(username);
		 
		 if(userDetails==null) {
			 throw new BadCredentialsException("Invalid Username");
		 }
		 
		 if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			 throw new BadCredentialsException("Invalid Password");
		 }
		 
		 return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
		
		
	}
	
	
	
	
	
}
