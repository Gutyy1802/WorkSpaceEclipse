package ceu.api.user.server.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceu.api.user.server.dto.request.ChangePasswordRequestDto;
import ceu.api.user.server.dto.request.LoginRequestDto;
import ceu.api.user.server.dto.request.UserCreateRequestDto;
import ceu.api.user.server.dto.response.UserResponseDto;
import ceu.dam.ad.user.model.User;
import ceu.dam.ad.user.services.DuplicateUserException;
import ceu.dam.ad.user.services.UserException;
import ceu.dam.ad.user.services.UserNotFoundException;
import ceu.dam.ad.user.services.UserService;
import ceu.dam.ad.user.services.UserUnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/user")
public class UserController {
	final UserService service;
	private final ModelMapper mapper;
	
	public UserController(UserService service) {
		this.service = service;
		this.mapper = new ModelMapper();
	}

	@PostMapping
	public UserResponseDto createUser(@RequestBody UserCreateRequestDto request) throws DuplicateUserException, UserException {
		User user = new ModelMapper().map(request, User.class);
		return mapper.map(service.createUser(user), UserResponseDto.class);
	}

	@Operation(summary = "cambio contraseña", description = "Permite cambiar la contraseña de un usuario")
	@PutMapping("/{id}")
	public void changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequestDto request) throws UserNotFoundException, UserUnauthorizedException, UserException {
		service.changePassword(id, request.getOldPassword(), request.getNewPassword());
	}

	@PostMapping("/login")
	public UserResponseDto login(@RequestBody LoginRequestDto request) throws UserNotFoundException, UserUnauthorizedException, UserException {
		return mapper.map(service.login(request.getLogin(), request.getPassword()), UserResponseDto.class); 
	}

	@GetMapping("/{id}")
	public UserResponseDto getUser(@PathVariable Long id) throws UserNotFoundException, UserException {
		return mapper.map(service.getUser(id), UserResponseDto.class); 
	}
}