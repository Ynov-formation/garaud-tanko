package com.ynov.msoperation.service;

import com.ynov.msaccount.entities.Account;
import com.ynov.msoperation.response.DepositResponse;
import com.ynov.msoperation.response.TransferResponse;
import com.ynov.msoperation.response.WithdrawResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {

	public static final String ACCOUNTS = "/accounts/";
	private static final String ACCOUNT_SERVICE_URL = "http://localhost:8082/account/v1";
	private final RestTemplate restTemplate;

	public OperationServiceImpl() {
		this.restTemplate = new RestTemplate();
	}

	@Override
	public DepositResponse deposit(Long accountId, Float amount) {
		Account account = restTemplate.getForObject(ACCOUNT_SERVICE_URL + ACCOUNTS + accountId, Account.class);

		if (account != null) {
			account.setBalance(account.getBalance() + amount);
			restTemplate.put(ACCOUNT_SERVICE_URL + ACCOUNTS + accountId, account);
			return DepositResponse.builder()
					.withAccountId(accountId)
					.withAmount(account.getBalance())
					.build();
		}
		return null;
	}

	@Override
	public WithdrawResponse withdraw(Long accountId, Float amount) {
		Account account = restTemplate.getForObject(ACCOUNT_SERVICE_URL + ACCOUNTS + accountId, Account.class);

		if (account != null && (account.getBalance() >= amount)) {
			account.setBalance(account.getBalance() - amount);
			restTemplate.put(ACCOUNT_SERVICE_URL + ACCOUNTS + accountId, account);
			return WithdrawResponse.builder()
					.withAccountId(accountId)
					.withAmount(account.getBalance())
					.build();
		}
		return null;
	}

	@Override
	public TransferResponse transfer(Long sourceAccountId, Float amount, Long destinationAccountId) {
		Account sourceAccount = restTemplate.getForObject(ACCOUNT_SERVICE_URL + ACCOUNTS + sourceAccountId, Account.class);
		Account destinationAccount = restTemplate.getForObject(ACCOUNT_SERVICE_URL + ACCOUNTS + destinationAccountId, Account.class);

		if (sourceAccount != null && destinationAccount != null && (sourceAccount.getBalance() >= amount)) {
			sourceAccount.setBalance(sourceAccount.getBalance() - amount);
			destinationAccount.setBalance(destinationAccount.getBalance() + amount);

			restTemplate.put(ACCOUNT_SERVICE_URL + ACCOUNTS + sourceAccountId, sourceAccount);
			restTemplate.put(ACCOUNT_SERVICE_URL + ACCOUNTS + destinationAccountId, destinationAccount);

			return TransferResponse.builder()
					.withSourceAccountId(sourceAccountId)
					.withDestinationAccountId(destinationAccountId)
					.withBalance(sourceAccount.getBalance())
					.build();
		}
		return null;
	}
}
