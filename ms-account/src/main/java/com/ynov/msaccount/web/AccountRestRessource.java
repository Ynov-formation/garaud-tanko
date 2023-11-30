package com.ynov.msaccount.web;

import com.ynov.msaccount.dao.AccountRepository;
import com.ynov.msaccount.entities.Account;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account/v1")
public class AccountRestRessource {

	private final AccountRepository accountRepository;

	public AccountRestRessource(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@GetMapping("/accounts")
	public List<Account> accountList() {
		return accountRepository.findAll();
	}

	@GetMapping("/accounts/{id}")
	public Account getAccount(@PathVariable(name = "id") Long id) {
		return accountRepository.findById(id).orElse(null);
	}

	@PostMapping("/accounts")
	public List<Account> saveAllAccounts(@RequestBody List<Account> accounts) {
		return accountRepository.saveAll(accounts);
	}

	@PutMapping("/accounts/{id}")
	public Account updateAccount(@PathVariable(name = "id") Long id, @RequestBody Account account) {
		account.setId(id);
		return accountRepository.save(account);
	}

	@DeleteMapping("/accounts")
	public void deleteAllAccounts() {
		accountRepository.deleteAll();
	}

	@DeleteMapping("/accounts/{id}")
	public void deleteAccount(@PathVariable(name = "id") Long id) {
		accountRepository.deleteById(id);
	}
}
