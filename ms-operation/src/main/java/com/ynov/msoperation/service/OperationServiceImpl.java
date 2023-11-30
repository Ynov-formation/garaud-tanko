package com.ynov.msoperation.service;

import com.ynov.msaccount.entities.Account;
import com.ynov.msoperation.dao.OperationRepository;
import com.ynov.msoperation.response.DepositResponse;
import com.ynov.msoperation.response.TransferResponse;
import com.ynov.msoperation.response.WithdrawResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class OperationServiceImpl implements OperationService {

	private final OperationRepository operationRepository;
	private final String clientServiceUrl = "http://localhost:8081/client/v1";
	private final String accountServiceUrl = "http://localhost:8082/account/v1";

	private final RestTemplate restTemplate;

	public OperationServiceImpl(OperationRepository operationRepository) {
		this.operationRepository = operationRepository;
		this.restTemplate = new RestTemplate();
	}

	@Override
	public DepositResponse deposit(Long accountId, Float amount) {
		Account account = restTemplate.getForObject(accountServiceUrl + "/accounts/" + accountId, Account.class);

		if (account != null) {
			account.setBalance(account.getBalance() + amount);
			restTemplate.put(accountServiceUrl + "/accounts/" + accountId, account);
			return DepositResponse.builder()
					.withAccountId(accountId)
					.withAmount(account.getBalance())
					.build();
		}
		return null;
	}

	@Override
	public WithdrawResponse withdraw(Long accountId, Float amount) {
		Account account = restTemplate.getForObject(accountServiceUrl + "/accounts/" + accountId, Account.class);

		if (account != null && (account.getBalance() >= amount)) {
			account.setBalance(account.getBalance() - amount);
			restTemplate.put(accountServiceUrl + "/accounts/" + accountId, account);
			return WithdrawResponse.builder()
					.withAccountId(accountId)
					.withAmount(account.getBalance())
					.build();
		}
		return null;
	}

	@Override
	public TransferResponse transfer(Long sourceAccountId, Float amount, Long destinationAccountId) {
		Account sourceAccount = restTemplate.getForObject(accountServiceUrl + "/accounts/" + sourceAccountId, Account.class);
		Account destinationAccount = restTemplate.getForObject(accountServiceUrl + "/accounts/" + destinationAccountId, Account.class);

		if (sourceAccount != null && destinationAccount != null && (sourceAccount.getBalance() >= amount)) {
			sourceAccount.setBalance(sourceAccount.getBalance() - amount);
			destinationAccount.setBalance(destinationAccount.getBalance() + amount);

			restTemplate.put(accountServiceUrl + "/accounts/" + sourceAccountId, sourceAccount);
			restTemplate.put(accountServiceUrl + "/accounts/" + destinationAccountId, destinationAccount);

			return TransferResponse.builder()
					.withSourceAccountId(sourceAccountId)
					.withDestinationAccountId(destinationAccountId)
					.withBalance(sourceAccount.getBalance())
					.build();
		}
		return null;
	}
}
