package com.ynov.msaccount.dao;

import com.ynov.msaccount.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
