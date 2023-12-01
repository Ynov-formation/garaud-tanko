package com.ynov.security.services;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends ReactiveUserDetailsService, UserDetailsService {
}
