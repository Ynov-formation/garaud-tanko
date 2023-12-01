package com.ynov.security.config;

import com.ynov.security.filter.JwtAuthenticationFilter;
import com.ynov.security.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ynov.security.entities.Role.USER;

@Configuration
@EnableWebFluxSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final AccountService accountService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(a -> a
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/**").hasRole(USER.name())
						.anyRequest().authenticated())
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(accountService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(authProvider);
	}

	@Bean
	public ReactiveAuthenticationManager reactiveAuthenticationManager() {
		UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
				new UserDetailsRepositoryReactiveAuthenticationManager(accountService);

		authenticationManager.setPasswordEncoder(passwordEncoder());

		return authenticationManager;
	}

}
