package com.ynov.msoperation.web;

import com.ynov.msoperation.dao.OperationRepository;
import com.ynov.msoperation.entities.Operation;
import com.ynov.msoperation.response.DepositResponse;
import com.ynov.msoperation.response.TransferResponse;
import com.ynov.msoperation.response.WithdrawResponse;
import com.ynov.msoperation.service.OperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/operation/v1")
public class OperationRestRessource {

	private final OperationRepository operationRepository;

	private final OperationService operationService;

	public OperationRestRessource(OperationRepository operationRepository, OperationService operationService) {
		this.operationRepository = operationRepository;
		this.operationService = operationService;
	}

	@GetMapping("/operations")
	public List<Operation> operationList() {
		return operationRepository.findAll();
	}

	@GetMapping("/operations/{id}")
	public ResponseEntity<Operation> getOperation(@PathVariable(name = "id") Long id) {
		Optional<Operation> operation = operationRepository.findById(id);

		return operation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/deposit")
	public ResponseEntity<DepositResponse> deposit(@RequestBody Operation operation) {
		DepositResponse deposit = operationService.deposit(
				operation.getAccountId(),
				operation.getAmount()
		);

		return deposit != null ? ResponseEntity.ok(deposit) : ResponseEntity.badRequest().build();
	}

	@PostMapping("/withdraw")
	public ResponseEntity<WithdrawResponse> withdraw(@RequestBody Operation operation) {
		WithdrawResponse withdraw = operationService.withdraw(
				operation.getAccountId(),
				operation.getAmount()
		);

		return withdraw != null ? ResponseEntity.ok(withdraw) : ResponseEntity.badRequest().build();
	}

	@PostMapping("/transfer")
	public ResponseEntity<TransferResponse> transfer(@RequestBody Operation operation) {
		TransferResponse transfer = operationService.transfer(
				operation.getSourceAccountId(),
				operation.getAmount(),
				operation.getDestinationAccountId()
		);

		return transfer != null ? ResponseEntity.ok(transfer) : ResponseEntity.badRequest().build();
	}
}
