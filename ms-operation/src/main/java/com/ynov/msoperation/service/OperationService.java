package com.ynov.msoperation.service;

import com.ynov.msoperation.response.DepositResponse;
import com.ynov.msoperation.response.TransferResponse;
import com.ynov.msoperation.response.WithdrawResponse;

public interface OperationService {
	DepositResponse deposit(Long accountId, Float amount);

	WithdrawResponse withdraw(Long accountId, Float amount);

	TransferResponse transfer(Long sourceAccountId, Float amount, Long destinationAccountId);
}
