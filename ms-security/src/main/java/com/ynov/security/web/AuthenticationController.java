package com.ynov.security.web;

import com.ynov.security.request.LoginRequest;
import com.ynov.security.request.SignUpRequest;
import com.ynov.security.response.JwtAuthenticationResponse;
import com.ynov.security.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	@PostMapping("/signup")
	public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
		return ResponseEntity.ok(authenticationService.signUp(request));
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request) {
		try {
			return ResponseEntity.ok(authenticationService.login(request));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
