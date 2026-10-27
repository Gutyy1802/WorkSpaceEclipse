package ceu.api.user.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserCreateRequestDto {
	
	private String username;
	private String name;
	private String email;
	@Schema(description = "Pasa password, sin cifrar")
	private String password;
}
