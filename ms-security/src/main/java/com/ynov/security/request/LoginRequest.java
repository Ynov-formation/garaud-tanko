package com.ynov.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
public class LoginRequest {

	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
