package ceu.api.user.server.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {

	private Long id;
	private String username;
	private String name;
	private String email;
	
}
