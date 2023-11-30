package com.ynov.msclient.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_client")
public class Client implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate birthDate;
}
