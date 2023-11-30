package com.ynov.msoperation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Entity
@Table(name = "t_operation")
public class Operation implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	private String type;
	private Float amount;
	private Long accountId;
	private Long clientId;
}
