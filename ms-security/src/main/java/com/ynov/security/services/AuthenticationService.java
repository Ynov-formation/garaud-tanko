package com.ynov.security.services;

import com.ynov.security.request.LoginRequest;
import com.ynov.security.request.SignUpRequest;
import com.ynov.security.response.JwtAuthenticationResponse;

public interface AuthenticationService {

	JwtAuthenticationResponse signUp(SignUpRequest request);

	JwtAuthenticationResponse login(LoginRequest request);

}
