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
	public Operation getOperation(@PathVariable(name = "id") Long id) {
		return operationRepository.findById(id).orElse(null);
	}

	@PostMapping("/deposit")
	public ResponseEntity<DepositResponse> deposit(@RequestBody Operation operation) {
		return ResponseEntity.ok(
				operationService.deposit(
						operation.getAccountId(),
						operation.getAmount()
				)
		);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<WithdrawResponse> withdraw(@RequestBody Operation operation) {
		return ResponseEntity.ok(
				operationService.withdraw(
						operation.getAccountId(),
						operation.getAmount()
				)
		);
	}

	@PostMapping("/transfer")
	public ResponseEntity<TransferResponse> transfer(@RequestBody Operation operation) {
		return ResponseEntity.ok(
				operationService.transfer(
						operation.getSourceAccountId(),
						operation.getAmount(),
						operation.getDestinationAccountId()
				)
		);
	}
}
