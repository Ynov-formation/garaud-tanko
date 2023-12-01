package com.ynov.security.services.impl;

import com.ynov.security.dao.UserRepository;
import com.ynov.security.entities.User;
import com.ynov.security.request.LoginRequest;
import com.ynov.security.request.SignUpRequest;
import com.ynov.security.response.JwtAuthenticationResponse;
import com.ynov.security.services.AuthenticationService;
import com.ynov.security.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ynov.security.entities.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public JwtAuthenticationResponse signUp(SignUpRequest request) {
		var user = User.builder()
				.withFirstName(request.getFirstName())
				.withLastName(request.getLastName())
				.withEmail(request.getEmail())
				.withPassword(passwordEncoder.encode(request.getPassword()))
				.withRole(USER)
				.build();

		userRepository.save(user);
		var jwt = jwtService.generateToken(user);
		return JwtAuthenticationResponse.builder()
				.withAccessToken(jwt)
				.withTokenType("Bearer")
				.build();
	}

	@Override
	public JwtAuthenticationResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
		);
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

		var jwt = jwtService.generateToken(user);

		return JwtAuthenticationResponse.builder()
				.withAccessToken(jwt)
				.withTokenType("Bearer")
				.build();
	}
}
