package ceu.api.user.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceu.api.user.server.dto.ChangePasswordRequestDto;
import ceu.api.user.server.dto.LoginRequestDto;
import ceu.dam.ad.user.model.User;
import ceu.dam.ad.user.services.DuplicateUserException;
import ceu.dam.ad.user.services.UserException;
import ceu.dam.ad.user.services.UserNotFoundException;
import ceu.dam.ad.user.services.UserService;
import ceu.dam.ad.user.services.UserUnauthorizedException;

@RestController
@RequestMapping("/user")
public class UserController {
	final UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	@PostMapping
	public User createUser(@RequestBody User user) throws DuplicateUserException, UserException {
		System.out.println("Insertando user..." + user);
		return service.createUser(user);
	}
	
	@PutMapping("/{id}")
	public void changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequestDto request) throws UserNotFoundException, UserUnauthorizedException, UserException {
		service.changePassword(id, request.getOldPassword(), request.getNewPassword());
	}
	
	@PostMapping("/login")
	public User login(@RequestBody LoginRequestDto request) throws UserNotFoundException, UserUnauthorizedException, UserException {
		return service.login(request.getLogin(), request.getPassword());
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) throws UserNotFoundException, UserException {
		return service.getUser(id);
	}
}
