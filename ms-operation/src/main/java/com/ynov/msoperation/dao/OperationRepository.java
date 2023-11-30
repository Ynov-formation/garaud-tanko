package com.ynov.msoperation.dao;

import com.ynov.msoperation.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
