package com.ynov.msoperation.web;

import com.ynov.msoperation.dao.OperationRepository;
import com.ynov.msoperation.entities.Operation;
import com.ynov.msoperation.service.OperationService;
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
	public Operation deposit(@RequestBody Operation operation) {
		Operation op = Operation.builder()
				.withType("deposit")
				.withAccountId(operation.getAccountId())
				.withClientId(operation.getClientId())
				.withAmount(operation.getAmount())
				.build();
		return operationRepository.save(op);
	}
}
